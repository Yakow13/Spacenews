package cz.weinzettl.spacenews.feature.article.domain.impl

import cz.weinzettl.spacenews.feature.article.domain.ArticleRepository
import cz.weinzettl.spacenews.feature.article.domain.GetArticleDetailUseCase
import cz.weinzettl.spacenews.feature.article.domain.model.ArticleDetail
import cz.weinzettl.spacenews.sdk.concurency.Dispatchers
import cz.weinzettl.spacenews.sdk.logger.logger
import kotlinx.coroutines.withContext

class DefaultGetArticleDetailUseCase(
    private val repository: ArticleRepository,
    private val dispatcher: Dispatchers
) : GetArticleDetailUseCase {
    override suspend fun invoke(articleId: Int): Result<ArticleDetail> =
        withContext(dispatcher.io) {
            runCatching {
                val detail = repository.getArticleDetail(articleId)
                requireNotNull(detail)
            }.onFailure {
                logger.error(it, "Error while getting article detail")
            }
        }
}