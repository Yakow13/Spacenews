package cz.weinzettl.spacenews.feature.homepage.service.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedArticleResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String?, // Nullable if it can be absent
    @SerialName("previous")
    val previous: String?, // Nullable if it can be absent
    @SerialName("results")
    val results: List<ArticleResponse>
)