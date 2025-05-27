package cz.weinzettl.spacenews.sdk.article.domain.impl

import cz.weinzettl.spacenews.sdk.article.domain.ArticleRepository
import cz.weinzettl.spacenews.sdk.article.domain.GetArticleDetailV2UseCase
import cz.weinzettl.spacenews.sdk.article.domain.model.ArticleDetailV2
import cz.weinzettl.spacenews.sdk.concurency.Dispatchers
import cz.weinzettl.spacenews.sdk.logger.logger
import kotlinx.coroutines.withContext

class DefaultGetArticleDetailV2UseCase(
    private val repository: ArticleRepository,
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