package cz.weinzettl.spacenews.feature.list.service.remote.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.weinzettl.spacenews.feature.list.service.remote.ArticleRemoteService
import cz.weinzettl.spacenews.feature.list.service.remote.api.ArticleApiService
import cz.weinzettl.spacenews.feature.list.service.remote.model.ArticleResponse
import cz.weinzettl.spacenews.feature.list.service.remote.model.PaginatedArticleResponse

@OptIn(ExperimentalPagingApi::class)
class DefaultArticleRemoteService(
    private val apiService: ArticleApiService
) : PagingSource<String, ArticleResponse>(), ArticleRemoteService {

//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, ArticleResponse>
//    ): MediatorResult {
//        try {
//            val pageKeyInfo = when (loadType) {
//                LoadType.REFRESH -> {
//                    val initialLimit = state.config.initialLoadSize
//                    apiService.getArticles(limit = initialLimit, offset = 0)
//                }
//
//                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//                LoadType.APPEND -> {
//                    val remoteKey = localService.getRemoteKeyByLabel(ARTICLE_LIST_NEXT_PAGE_KEY_RM)
//                    if (remoteKey?.nextKey == null) {
//                        return MediatorResult.Success(endOfPaginationReached = true)
//                    }
//                    remoteKey.nextKey
//                }
//            }
//
//            val response = if (pageKeyInfo == null) {
//                val initialLimit = state.config.initialLoadSize
//                apiService.getArticles(limit = initialLimit, offset = 0)
//            } else {
//                remoteService.getArticlesByUrl(pageKeyInfo)
//            }
//
//            val articlesFromNetwork = response.results
//            val endOfPaginationReached = response.next == null
//
//            // The local service method now handles the transaction
//            // The `database.withTransaction` block is now inside localService.insertArticlesAndRemoteKey
//            localService.insertArticlesAndRemoteKey(
//                articles = articlesFromNetwork.toEntities(),
//                nextKey = response.next,
//                isRefresh = (loadType == LoadType.REFRESH),
//                remoteKeyLabel = ARTICLE_LIST_NEXT_PAGE_KEY_RM
//            )
//
//            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
//
//        } catch (e: IOException) {
//            return MediatorResult.Error(e)
//        } catch (e: HttpException) {
//            return MediatorResult.Error(e)
//        } catch (e: Exception) {
//            return MediatorResult.Error(e)
//        }
//    }

    override suspend fun getArticles(
        limit: Int,
        offset: Int
    ): PaginatedArticleResponse =
        apiService.getArticles(limit, offset)

    override fun getRefreshKey(state: PagingState<String, ArticleResponse>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ArticleResponse> {
        return try {
            val response: PaginatedArticleResponse = when (params) {
                is LoadParams.Refresh -> {
                    // Initial load or refresh - use the initial call from your remoteService
                    // The 'key' (params.key) will be null for the first load.
                    apiService.getArticles(
                        limit = NETWORK_PAGE_SIZE_CONFIG,
                        offset = 0
                    ) // Or params.loadSize
                }

                is LoadParams.Append -> {
                    // Loading next page
                    val nextUrl = params.key
                    if (nextUrl.isEmpty()) {
                        // Should ideally not happen if API provides "next" correctly and we don't pass an empty key
                        return LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
                    }
                    apiService.getArticlesByUrl(nextUrl)
                }

                is LoadParams.Prepend -> {
                    // Loading previous page using the "previous" URL
                    val prevUrl = params.key
                    if (prevUrl.isEmpty()) {
                        return LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
                    }
                    apiService.getArticlesByUrl(prevUrl)
                }
            }

            LoadResult.Page(
                data = response.results,
                prevKey = response.previous, // Pass the "previous" URL from API
                nextKey = response.next      // Pass the "next" URL from API
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE_CONFIG = 30
    }
}