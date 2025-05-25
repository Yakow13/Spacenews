package cz.weinzettl.spacenews.feature.homepage.service.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchResponse(
    @SerialName("launch_id")
    val launchId: String, // Assuming UUID is represented as a String
    @SerialName("provider")
    val provider: String
)