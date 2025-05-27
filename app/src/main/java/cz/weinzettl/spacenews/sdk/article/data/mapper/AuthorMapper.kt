package cz.weinzettl.spacenews.sdk.article.data.mapper

import cz.weinzettl.spacenews.sdk.article.data.local.model.AuthorEntity
import cz.weinzettl.spacenews.sdk.article.domain.model.Author


object AuthorMapper {

    fun toDomain(authorEntity: AuthorEntity): Author =
        Author(
            name = authorEntity.name
        )
}