package cz.weinzettl.spacenews.feature.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    data object Home : Destination

    data object Detail : Destination
}