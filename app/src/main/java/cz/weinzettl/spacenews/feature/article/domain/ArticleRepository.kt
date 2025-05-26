package cz.weinzettl.spacenews.feature.article.domain

import androidx.paging.PagingData
import cz.weinzettl.spacenews.feature.article.domain.model.Article
import cz.weinzettl.spacenews.feature.article.domain.model.ArticleDetail
import cz.weinzettl.spacenews.feature.article.domain.model.ArticleDetailV2
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticlesStream(): Flow<PagingData<Article>>

    suspend fun getArticleDetail(id: Int): ArticleDetail?

    suspend fun getArticleDetailV2(id: Int): ArticleDetailV2?

}