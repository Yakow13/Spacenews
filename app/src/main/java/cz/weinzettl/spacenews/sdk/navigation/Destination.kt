package cz.weinzettl.spacenews.sdk.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    data object Home : Destination

    @Serializable
    data class Detail(val articleId: Int) : Destination
}