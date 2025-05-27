package cz.weinzettl.spacenews.feature.article.data.mapper

import cz.weinzettl.spacenews.feature.article.data.local.model.AuthorEntity
import cz.weinzettl.spacenews.feature.article.domain.model.Author

object AuthorMapper {

    fun toDomain(authorEntity: AuthorEntity): Author =
        Author(
            name = authorEntity.name
        )
}