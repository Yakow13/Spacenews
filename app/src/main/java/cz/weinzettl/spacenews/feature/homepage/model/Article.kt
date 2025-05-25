package cz.weinzettl.spacenews.feature.homepage.model

data class Article(
    val id: Int,
    val title: String,
    val summary: String,
    val imageUrl: String?,
    val publishedAt: String
)
