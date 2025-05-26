package cz.weinzettl.spacenews.feature.article.service.remote.api

import cz.weinzettl.spacenews.feature.article.service.remote.model.PaginatedArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    @GET("articles")
    suspend fun getArticles(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PaginatedArticleResponse
}