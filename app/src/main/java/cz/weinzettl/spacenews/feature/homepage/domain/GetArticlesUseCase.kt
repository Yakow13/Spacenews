package cz.weinzettl.spacenews.feature.homepage.domain

import androidx.paging.PagingData
import cz.weinzettl.spacenews.feature.homepage.model.Article
import kotlinx.coroutines.flow.Flow

interface GetArticlesUseCase {
    operator fun invoke(): Result<Flow<PagingData<Article>>>
}