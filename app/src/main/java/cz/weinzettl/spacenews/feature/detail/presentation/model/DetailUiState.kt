package cz.weinzettl.spacenews.feature.detail.presentation.model

//data class DetailUiState(
//    val articleUrl: String? = null,
//    val isLoading: Boolean = true,
//    val error: String? = null,
//    val pageTitle: String? = null
//)

sealed interface DetailUiState {
    data class Idle(
        val articleUrl: String,
    ) : DetailUiState

    object Loading : DetailUiState
    object Empty : DetailUiState
}
