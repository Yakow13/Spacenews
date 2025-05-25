package cz.weinzettl.spacenews.feature.article.service.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocialsResponse(
    @SerialName("x")
    val x: String?, // Assuming these can be optional/null
    @SerialName("youtube")
    val youtube: String?,
    @SerialName("instagram")
    val instagram: String?,
    @SerialName("linkedin")
    val linkedin: String?,
    @SerialName("mastodon")
    val mastodon: String?,
    @SerialName("bluesky")
    val bluesky: String?
)