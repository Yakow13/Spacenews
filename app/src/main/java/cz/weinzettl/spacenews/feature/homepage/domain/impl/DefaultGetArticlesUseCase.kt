package cz.weinzettl.spacenews.feature.homepage.domain.impl

import androidx.paging.PagingData
import cz.weinzettl.spacenews.feature.homepage.data.HomePageRepository
import cz.weinzettl.spacenews.feature.homepage.domain.GetArticlesUseCase
import cz.weinzettl.spacenews.feature.homepage.model.Article
import cz.weinzettl.spacenews.sdk.logger.logger
import kotlinx.coroutines.flow.Flow

class DefaultGetArticlesUseCase(private val homePageRepository: HomePageRepository) :
    GetArticlesUseCase {

    override operator fun invoke(): Result<Flow<PagingData<Article>>> =
        runCatching {
            homePageRepository.getArticlesStream()
        }.onFailure {
            logger.error(it, "Failed to get articles")
        }
}