package cz.weinzettl.spacenews.feature.article.data

import androidx.paging.PagingData
import cz.weinzettl.spacenews.feature.article.model.Article
import cz.weinzettl.spacenews.feature.article.model.ArticleDetail
import cz.weinzettl.spacenews.feature.article.model.ArticleDetailV2
import kotlinx.coroutines.flow.Flow

interface HomePageRepository {
    fun getArticlesStream(): Flow<PagingData<Article>>

    suspend fun getArticleDetail(id: Int): ArticleDetail?

    suspend fun getArticleDetailV2(id: Int): ArticleDetailV2?

}