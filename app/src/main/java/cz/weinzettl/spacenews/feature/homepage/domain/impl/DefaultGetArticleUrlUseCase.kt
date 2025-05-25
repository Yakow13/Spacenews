package cz.weinzettl.spacenews.feature.homepage.domain.impl

import cz.weinzettl.spacenews.feature.homepage.data.HomePageRepository
import cz.weinzettl.spacenews.feature.homepage.domain.GetArticleUrlUseCase
import cz.weinzettl.spacenews.sdk.concurency.service.Dispatchers
import kotlinx.coroutines.withContext

class DefaultGetArticleUrlUseCase(
    private val repository: HomePageRepository,
    private val dispatcher: Dispatchers
) : GetArticleUrlUseCase {
    override suspend fun invoke(articleId: Int): Result<String> = withContext(dispatcher.io) {

    }
}