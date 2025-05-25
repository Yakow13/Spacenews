package cz.weinzettl.spacenews.feature.homepage.service.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    @SerialName("event_id")
    val eventId: Int,
    @SerialName("provider")
    val provider: String
)