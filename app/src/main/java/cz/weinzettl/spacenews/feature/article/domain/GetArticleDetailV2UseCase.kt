package cz.weinzettl.spacenews.feature.article.domain

import cz.weinzettl.spacenews.feature.article.domain.model.ArticleDetailV2

interface GetArticleDetailV2UseCase {
    suspend operator fun invoke(articleId: Int): Result<ArticleDetailV2>
}