package cz.weinzettl.spacenews.feature.article.data.mapper

import cz.weinzettl.spacenews.feature.article.data.local.model.ArticleEntity
import cz.weinzettl.spacenews.feature.article.data.local.model.AuthorEntity
import cz.weinzettl.spacenews.feature.article.data.remote.model.ArticleResponse
import cz.weinzettl.spacenews.feature.article.domain.model.Article

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

    fun toDomain(articleEntity: ArticleEntity): Article =
        articleEntity.run {
            Article(
                id = id,
                title = title,
                summary = summary,
                imageUrl = imageUrl,
            )
        }
}
