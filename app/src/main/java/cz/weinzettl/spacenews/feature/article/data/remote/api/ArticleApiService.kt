package cz.weinzettl.spacenews.feature.article.data.remote.api

import cz.weinzettl.spacenews.feature.article.data.remote.model.PaginatedArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {
    @GET("articles")
    suspend fun getArticles(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PaginatedArticleResponse
}