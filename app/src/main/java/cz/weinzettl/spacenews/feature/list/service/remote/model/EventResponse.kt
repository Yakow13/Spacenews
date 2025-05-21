package cz.weinzettl.spacenews.feature.list.service.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    @SerialName("event_id")
    val eventId: Int,
    @SerialName("provider")
    val provider: String
)