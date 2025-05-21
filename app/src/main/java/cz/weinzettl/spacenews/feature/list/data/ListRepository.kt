package cz.weinzettl.spacenews.feature.list.data

import cz.weinzettl.spacenews.feature.list.service.remote.model.PaginatedArticleResponse
import kotlinx.coroutines.flow.Flow

interface ListRepository {
    suspend fun fetchArticlesPage(
        url: String? = null,
        limit: Int,
        initialOffset: Int = 0
    ): Flow<Result<PaginatedArticleResponse>>
}