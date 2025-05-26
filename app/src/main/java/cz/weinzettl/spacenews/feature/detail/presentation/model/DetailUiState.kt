package cz.weinzettl.spacenews.feature.detail.presentation.model

sealed interface DetailUiState {
    data class Idle(
        val articleUrl: String,
    ) : DetailUiState

    object Loading : DetailUiState
    object Empty : DetailUiState
}
