package cz.weinzettl.spacenews.feature.article.data.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.map
import cz.weinzettl.spacenews.feature.article.domain.ArticleRepository
import cz.weinzettl.spacenews.feature.article.domain.model.Article
import cz.weinzettl.spacenews.feature.article.domain.model.ArticleDetail
import cz.weinzettl.spacenews.feature.article.domain.model.ArticleDetailV2
import cz.weinzettl.spacenews.feature.article.service.local.dao.ArticleDao
import cz.weinzettl.spacenews.feature.article.service.local.dao.RemoteKeyDao
import cz.weinzettl.spacenews.feature.article.service.local.model.ArticleEntity
import cz.weinzettl.spacenews.feature.article.service.local.model.RemoteKey
import cz.weinzettl.spacenews.feature.article.service.mapper.ArticleDetailMapper.toDetailDomain
import cz.weinzettl.spacenews.feature.article.service.mapper.ArticleDetailMapper.toDetailDomainV2
import cz.weinzettl.spacenews.feature.article.service.mapper.ArticleMapper
import cz.weinzettl.spacenews.feature.article.service.remote.api.ArticleApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class DefaultArticleRepository(
    private val apiService: ArticleApiService,
    private val articleDao: ArticleDao,
    private val remoteKeyDao: RemoteKeyDao
) : ArticleRepository, RemoteMediator<Int, ArticleEntity>() {

    override fun getArticlesStream(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = PREFETCH_DISTANCE
            ),
            remoteMediator = this,
            pagingSourceFactory = { articleDao.getPagingSource() }

        ).flow.map { pagingEntity ->
            pagingEntity.map(ArticleMapper::toDomain)
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult =
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(state.config.pageSize) ?: STARTING_OFFSET_INDEX
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKeys.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                        ?: return MediatorResult.Success(endOfPaginationReached = false)
                    remoteKeys.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val apiResponse = apiService.getArticles(loadKey, state.config.pageSize)
            val articles = apiResponse.results
            val endOfPaginationReached =
                articles.isEmpty() || (apiResponse.next == null && apiResponse.previous != null)

            if (loadType == LoadType.REFRESH) {
                remoteKeyDao.clearAll()
                articleDao.clearAll()
            }
            val prevOffset =
                if (loadKey == STARTING_OFFSET_INDEX) null else loadKey - state.config.pageSize
            val nextOffset =
                if (endOfPaginationReached) null else loadKey + state.config.pageSize

            val keys = articles.map { article ->
                RemoteKey(articleId = article.id, prevKey = prevOffset, nextKey = nextOffset)
            }
            remoteKeyDao.insertOrReplaceAll(keys)
            articleDao.insertAll(articles.map(ArticleMapper::toEntity))
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ArticleEntity>): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article -> remoteKeyDao.getRemoteKeyById(article.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ArticleEntity>): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article -> remoteKeyDao.getRemoteKeyById(article.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ArticleEntity>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { articleId ->
                remoteKeyDao.getRemoteKeyById(articleId)
            }
        }
    }

    override suspend fun getArticleDetail(id: Int): ArticleDetail? {
        return articleDao.getArticleById(id)?.toDetailDomain()
    }

    override suspend fun getArticleDetailV2(id: Int): ArticleDetailV2? {
        return articleDao.getArticleById(id)?.toDetailDomainV2()
    }

    companion object {
        private const val STARTING_OFFSET_INDEX = 0
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 5
    }
}
