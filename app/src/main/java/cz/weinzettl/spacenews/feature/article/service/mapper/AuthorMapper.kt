package cz.weinzettl.spacenews.feature.article.service.mapper

import cz.weinzettl.spacenews.feature.article.domain.model.Author
import cz.weinzettl.spacenews.feature.article.service.local.model.AuthorEntity

object AuthorMapper {

    fun toDomain(authorEntity: AuthorEntity): Author =
        Author(
            name = authorEntity.name
        )
}