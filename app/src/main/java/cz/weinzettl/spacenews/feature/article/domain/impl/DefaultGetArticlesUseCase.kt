package cz.weinzettl.spacenews.feature.article.domain.impl

import androidx.paging.PagingData
import cz.weinzettl.spacenews.feature.article.domain.ArticleRepository
import cz.weinzettl.spacenews.feature.article.domain.GetArticlesUseCase
import cz.weinzettl.spacenews.feature.article.domain.model.Article
import cz.weinzettl.spacenews.sdk.logger.logger
import kotlinx.coroutines.flow.Flow

class DefaultGetArticlesUseCase(
    private val articleRepository: ArticleRepository
) : GetArticlesUseCase {

    override operator fun invoke(): Result<Flow<PagingData<Article>>> =
        runCatching {
            articleRepository.getArticlesStream()
        }.onFailure {
            logger.error(it, "Failed to get articles")
        }
}