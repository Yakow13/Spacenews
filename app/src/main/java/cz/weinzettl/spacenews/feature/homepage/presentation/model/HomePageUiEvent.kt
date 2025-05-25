package cz.weinzettl.spacenews.feature.homepage.presentation.model

import androidx.annotation.StringRes

sealed interface HomePageUiEvent {
    data class Error(@StringRes val messageRes: Int) : HomePageUiEvent
}