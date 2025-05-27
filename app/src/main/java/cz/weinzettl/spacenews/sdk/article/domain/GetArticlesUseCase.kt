package cz.weinzettl.spacenews.sdk.article.domain

import androidx.paging.PagingData
import cz.weinzettl.spacenews.sdk.article.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface GetArticlesUseCase {
    operator fun invoke(): Result<Flow<PagingData<Article>>>
}