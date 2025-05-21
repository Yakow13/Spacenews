package cz.weinzettl.spacenews.feature.list.data.impl

import cz.weinzettl.spacenews.feature.list.data.ListRepository
import cz.weinzettl.spacenews.feature.list.service.remote.api.ArticleApiService
import cz.weinzettl.spacenews.feature.list.service.remote.model.PaginatedArticleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultListRepository(
    private val apiService: ArticleApiService
) : ListRepository {
    override suspend fun fetchArticlesPage(
        url: String?,
        limit: Int,
        initialOffset: Int
    ): Flow<Result<PaginatedArticleResponse>> = flow {
        try {
            val response = if (url != null) {
                apiService.getArticlesByUrl(url)
            } else {
                apiService.getArticles(limit = limit, offset = initialOffset)
            }
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}