package cz.weinzettl.spacenews.feature.homepage.service.mapper

import cz.weinzettl.spacenews.feature.homepage.model.Article
import cz.weinzettl.spacenews.feature.homepage.service.local.model.ArticleEntity
import cz.weinzettl.spacenews.feature.homepage.service.remote.model.ArticleResponse

object ArticleMapper {

    fun toEntity(articleResponse: ArticleResponse): ArticleEntity =
        articleResponse.run {
            ArticleEntity(
                id = id,
                title = title,
                summary = summary,
                imageUrl = imageUrl,
                url = url,
                publishedAt = publishedAt
            )
        }

    fun ArticleEntity.toDomain(): Article =
        Article(
            id = id,
            title = title,
            summary = summary,
            imageUrl = imageUrl,
            publishedAt = publishedAt
        )
}
