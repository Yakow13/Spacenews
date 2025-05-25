package cz.weinzettl.spacenews.feature.homepage.service.remote.api

import cz.weinzettl.spacenews.feature.homepage.service.remote.model.PaginatedArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ArticleApiService {


    @GET("articles")
    suspend fun getArticles(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PaginatedArticleResponse

    @GET
    suspend fun getArticlesByUrl(@Url url: String): PaginatedArticleResponse
}