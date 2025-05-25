package cz.weinzettl.spacenews.feature.article.domain

import androidx.paging.PagingData
import cz.weinzettl.spacenews.feature.article.model.Article
import kotlinx.coroutines.flow.Flow

interface GetArticlesUseCase {
    operator fun invoke(): Result<Flow<PagingData<Article>>>
}