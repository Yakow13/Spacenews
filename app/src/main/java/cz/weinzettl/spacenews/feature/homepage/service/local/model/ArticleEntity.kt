package cz.weinzettl.spacenews.feature.homepage.service.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val summary: String,
    val imageUrl: String?,
    val url: String,
    val publishedAt: String
)
