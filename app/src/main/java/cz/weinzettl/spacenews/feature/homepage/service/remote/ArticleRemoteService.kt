package cz.weinzettl.spacenews.feature.homepage.service.remote

import cz.weinzettl.spacenews.feature.homepage.service.remote.model.PaginatedArticleResponse

interface ArticleRemoteService {

    suspend fun getArticles(limit: Int, offset: Int): PaginatedArticleResponse

}