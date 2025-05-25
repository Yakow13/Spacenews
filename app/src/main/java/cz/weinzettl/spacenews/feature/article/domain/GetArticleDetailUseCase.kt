package cz.weinzettl.spacenews.feature.article.domain

import cz.weinzettl.spacenews.feature.article.model.ArticleDetail

interface GetArticleDetailUseCase {
    suspend operator fun invoke(articleId: Int): Result<ArticleDetail>
}