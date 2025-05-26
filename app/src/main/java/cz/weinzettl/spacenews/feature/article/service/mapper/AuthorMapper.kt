package cz.weinzettl.spacenews.feature.article.service.mapper

import cz.weinzettl.spacenews.feature.article.domain.model.Author
import cz.weinzettl.spacenews.feature.article.service.local.model.AuthorEntity

object AuthorMapper {
    fun AuthorEntity.toDomain(): Author =
        Author(
            name = name
        )
}