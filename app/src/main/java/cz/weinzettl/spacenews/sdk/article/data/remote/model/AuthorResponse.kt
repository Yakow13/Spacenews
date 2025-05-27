package cz.weinzettl.spacenews.sdk.article.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorResponse(
    @SerialName("name")
    val name: String
)
