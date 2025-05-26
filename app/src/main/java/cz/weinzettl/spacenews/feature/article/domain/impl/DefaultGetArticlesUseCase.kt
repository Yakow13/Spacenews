package cz.weinzettl.spacenews.feature.article.domain.impl

import androidx.paging.PagingData
import cz.weinzettl.spacenews.feature.article.domain.ArticleRepository
import cz.weinzettl.spacenews.feature.article.domain.GetArticlesUseCase
import cz.weinzettl.spacenews.feature.article.domain.model.Article
import cz.weinzettl.spacenews.sdk.concurency.Dispatchers
import cz.weinzettl.spacenews.sdk.logger.logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DefaultGetArticlesUseCase(
    private val articleRepository: ArticleRepository,
    private val dispatchers: Dispatchers
) : GetArticlesUseCase {

    override operator fun invoke(): Result<Flow<PagingData<Article>>> =
        runCatching {
            articleRepository
                .getArticlesStream()
                .flowOn(dispatchers.io)
        }.onFailure {
            logger.error(it, "Failed to get articles")
        }
}