package cz.weinzettl.spacenews.feature.article.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorResponse(
    @SerialName("name")
    val name: String
)
