package cz.weinzettl.spacenews.feature.article.service.local

import androidx.room.TypeConverter
import cz.weinzettl.spacenews.feature.article.service.local.model.AuthorEntity

class AuthorNameListConverter {

    @TypeConverter
    fun fromAuthorList(authors: List<AuthorEntity>?): String? {
        if (authors.isNullOrEmpty()) {
            return null
        }
        return authors.joinToString(separator = DELIMITER) { authorEntity ->
            authorEntity.name
        }
    }

    @TypeConverter
    fun toAuthorList(data: String?): List<AuthorEntity> { // Returns non-nullable List<String>
        if (data.isNullOrBlank()) {
            return emptyList()
        }
        return data.split(DELIMITER)
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map {
                AuthorEntity(it)
            }
    }

    companion object {
        private const val DELIMITER = ","

    }
}