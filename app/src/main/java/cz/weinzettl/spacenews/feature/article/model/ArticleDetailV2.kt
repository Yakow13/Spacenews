package cz.weinzettl.spacenews.feature.article.model

data class ArticleDetailV2(
    val title: String,
    val authors: List<Author>,
    val url: String,
    val imageUrl: String?,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
)

data class Author(
    val name: String,
)