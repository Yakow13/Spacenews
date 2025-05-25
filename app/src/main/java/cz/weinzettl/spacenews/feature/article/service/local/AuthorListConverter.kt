package cz.weinzettl.spacenews.feature.article.service.local

import androidx.room.TypeConverter
import cz.weinzettl.spacenews.feature.article.service.local.model.AuthorEntity

class AuthorNameListConverter {

    private val delimiter = ","

    @TypeConverter
    fun fromAuthorList(authors: List<AuthorEntity>?): String? {
        if (authors.isNullOrEmpty()) {
            return null
        }
        return authors.joinToString(separator = delimiter)
    }

    @TypeConverter
    fun toAuthorList(data: String?): List<AuthorEntity> { // Returns non-nullable List<String>
        if (data.isNullOrBlank()) {
            return emptyList()
        }
        return data.split(delimiter)
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map {
                AuthorEntity(it)
            }
    }
}