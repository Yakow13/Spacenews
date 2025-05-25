package cz.weinzettl.spacenews.feature.homepage.data.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.map
import androidx.room.withTransaction
import cz.weinzettl.spacenews.feature.homepage.data.HomePageRepository
import cz.weinzettl.spacenews.feature.homepage.model.Article
import cz.weinzettl.spacenews.feature.homepage.service.local.dao.ArticleDao
import cz.weinzettl.spacenews.feature.homepage.service.local.dao.RemoteKeyDao
import cz.weinzettl.spacenews.feature.homepage.service.local.model.ArticleEntity
import cz.weinzettl.spacenews.feature.homepage.service.local.model.RemoteKey
import cz.weinzettl.spacenews.feature.homepage.service.mapper.ArticleMapper
import cz.weinzettl.spacenews.feature.homepage.service.mapper.ArticleMapper.toDomain
import cz.weinzettl.spacenews.feature.homepage.service.remote.api.ArticleApiService
import cz.weinzettl.spacenews.sdk.database.service.SpaceNewsDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class DefaultHomePageRepository(
    private val apiService: ArticleApiService,
    private val database: SpaceNewsDatabase,
    private val articleDao: ArticleDao,
    private val remoteKeyDao: RemoteKeyDao
) : HomePageRepository, RemoteMediator<Int, ArticleEntity>() {

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticlesStream(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // Number of items to load per page from API
                enablePlaceholders = false, // Set to true if you want placeholders for unloaded items
                prefetchDistance = 5 // How many items to fetch before the current view
            ),
            remoteMediator = this,
            pagingSourceFactory = { database.articleDao().getPagingSource() }

        ).flow.map {
            it.map { it.toDomain() }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult =
        try {
            // Determine the page offset to request from the API based on LoadType
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    // Start refresh at the beginning of the list, or from a specific key if needed
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(state.config.pageSize) ?: STARTING_OFFSET_INDEX
                }

                LoadType.PREPEND -> {
                    // Prepend means loading data to the beginning (older data)
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                        ?: return MediatorResult.Success(endOfPaginationReached = true) // No more data to prepend
                    remoteKeys.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true) // No previous page
                }

                LoadType.APPEND -> {
                    // Append means loading data to the end (newer data)
                    val remoteKeys = getRemoteKeyForLastItem(state)
                        ?: return MediatorResult.Success(endOfPaginationReached = false) // Initial load, not end
                    remoteKeys.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true) // No more data to append
                }
            }

            // Fetch articles from the API
            val apiResponse = apiService.getArticles(loadKey, state.config.pageSize)
            val articles = apiResponse.results
            val endOfPaginationReached =
                articles.isEmpty() || (apiResponse.next == null && apiResponse.previous != null) // Check if 'next' is null for end

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearAll()
                    articleDao.clearAll()
                }
                val prevOffset =
                    if (loadKey == STARTING_OFFSET_INDEX) null else loadKey - state.config.pageSize
                val nextOffset =
                    if (endOfPaginationReached) null else loadKey + state.config.pageSize

                // Insert new RemoteKeys for each article
                val keys = articles.map { article ->
                    RemoteKey(articleId = article.id, prevKey = prevOffset, nextKey = nextOffset)
                }
                remoteKeyDao.insertOrReplaceAll(keys)
                articleDao.insertAll(articles.map(ArticleMapper::toEntity)) // Insert articles into Room
                return@withTransaction MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
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

    override suspend fun getArticleUrlById(id: Int): String? {
        return articleDao.getArticleUrlById(id)
    }

    companion object {
        private const val STARTING_OFFSET_INDEX = 0
    }
}
