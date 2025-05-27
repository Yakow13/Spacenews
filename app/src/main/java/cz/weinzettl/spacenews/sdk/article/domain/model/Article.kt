package cz.weinzettl.spacenews.sdk.article.domain.model

data class Article(
    val id: Int,
    val title: String,
    val summary: String,
    val imageUrl: String?,
)
