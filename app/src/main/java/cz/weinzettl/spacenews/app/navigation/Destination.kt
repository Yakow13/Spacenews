package cz.weinzettl.spacenews.app.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    data object Home : Destination

    @Serializable
    data class Detail(val articleId: Int) : Destination

    @Serializable
    data class DetailV2(val articleId: Int) : Destination
}