package cz.weinzettl.spacenews.feature.article.service.mapper

import cz.weinzettl.spacenews.feature.article.model.Article
import cz.weinzettl.spacenews.feature.article.service.local.model.ArticleEntity
import cz.weinzettl.spacenews.feature.article.service.local.model.AuthorEntity
import cz.weinzettl.spacenews.feature.article.service.remote.model.ArticleResponse

object ArticleMapper {

    fun toEntity(articleResponse: ArticleResponse): ArticleEntity =
        articleResponse.run {
            ArticleEntity(
                id = id,
                title = title,
                summary = summary,
                imageUrl = imageUrl,
                url = url,
                publishedAt = publishedAt,
                newsSite = newsSite,
                authors = authors.map { author ->
                    AuthorEntity(
                        name = author.name
                    )
                }
            )
        }

    fun ArticleEntity.toDomain(): Article =
        Article(
            id = id,
            title = title,
            summary = summary,
            imageUrl = imageUrl,
        )
}
