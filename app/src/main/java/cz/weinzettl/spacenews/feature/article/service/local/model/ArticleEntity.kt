package cz.weinzettl.spacenews.feature.article.service.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import cz.weinzettl.spacenews.feature.article.service.local.AuthorNameListConverter

@Entity(tableName = "articles")
@TypeConverters(AuthorNameListConverter::class)
data class ArticleEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val authors: List<AuthorEntity>,
    val summary: String,
    val imageUrl: String?,
    val newsSite: String,
    val url: String,
    val publishedAt: String
)
