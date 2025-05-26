package cz.weinzettl.spacenews.feature.article.domain.model

data class Article(
    val id: Int,
    val title: String,
    val summary: String,
    val imageUrl: String?,
)
