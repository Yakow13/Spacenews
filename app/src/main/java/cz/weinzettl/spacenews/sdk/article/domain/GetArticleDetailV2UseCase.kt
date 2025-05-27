package cz.weinzettl.spacenews.sdk.article.domain

import cz.weinzettl.spacenews.sdk.article.domain.model.ArticleDetailV2


interface GetArticleDetailV2UseCase {
    suspend operator fun invoke(articleId: Int): Result<ArticleDetailV2>
}