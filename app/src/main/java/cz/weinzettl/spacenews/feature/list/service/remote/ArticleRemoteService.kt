package cz.weinzettl.spacenews.feature.list.service.remote

import cz.weinzettl.spacenews.feature.list.service.remote.model.PaginatedArticleResponse

interface ArticleRemoteService {

    suspend fun getArticles(limit: Int, offset: Int): PaginatedArticleResponse

}