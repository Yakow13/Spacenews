package cz.weinzettl.spacenews.feature.article.domain.impl

import cz.weinzettl.spacenews.feature.article.data.HomePageRepository
import cz.weinzettl.spacenews.feature.article.domain.GetArticleDetailV2UseCase
import cz.weinzettl.spacenews.feature.article.model.ArticleDetailV2
import cz.weinzettl.spacenews.sdk.concurency.service.Dispatchers
import cz.weinzettl.spacenews.sdk.logger.logger
import kotlinx.coroutines.withContext

class DefaultGetArticleDetailV2UseCase(
    private val repository: HomePageRepository,
    private val dispatcher: Dispatchers
) : GetArticleDetailV2UseCase {


    override suspend fun invoke(articleId: Int): Result<ArticleDetailV2> =
        withContext(dispatcher.io) {
            runCatching {
                val detail = repository.getArticleDetailV2(articleId)
                requireNotNull(detail)
            }.onFailure {
                logger.error(it, "Error while getting article detail V2")
            }
        }
}