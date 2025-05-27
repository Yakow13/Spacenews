package cz.weinzettl.spacenews.sdk.article.domain

import cz.weinzettl.spacenews.sdk.article.domain.model.ArticleDetail


interface GetArticleDetailUseCase {
    suspend operator fun invoke(articleId: Int): Result<ArticleDetail>
}