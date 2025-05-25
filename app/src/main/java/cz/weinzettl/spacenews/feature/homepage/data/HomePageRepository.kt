package cz.weinzettl.spacenews.feature.homepage.data

import androidx.paging.PagingData
import cz.weinzettl.spacenews.feature.homepage.model.Article
import kotlinx.coroutines.flow.Flow

interface HomePageRepository {
    fun getArticlesStream(): Flow<PagingData<Article>>

    suspend fun getArticleUrlById(id: Int): String?
}