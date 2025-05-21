package cz.weinzettl.spacenews.feature.list.service.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorResponse(
    @SerialName("name")
    val name: String,
    @SerialName("socials")
    val socials: SocialsResponse
)
