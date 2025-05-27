package cz.weinzettl.spacenews.feature.article.data.mapper

import cz.weinzettl.spacenews.feature.article.data.local.model.ArticleEntity
import cz.weinzettl.spacenews.feature.article.domain.model.ArticleDetail
import cz.weinzettl.spacenews.feature.article.domain.model.ArticleDetailV2

object ArticleDetailMapper {

    fun ArticleEntity.toDetailDomain(): ArticleDetail =
        ArticleDetail(
            title = title,
            url = url
        )

    fun ArticleEntity.toDetailDomainV2(): ArticleDetailV2 =
        ArticleDetailV2(
            title = title,
            url = url,
            imageUrl = imageUrl,
            summary = summary,
            newsSite = newsSite,
            publishedAt = publishedAt,
            authors = authors.map(AuthorMapper::toDomain),
        )

}