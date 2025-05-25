package cz.weinzettl.spacenews.feature.detailv2.presentation.model

import cz.weinzettl.spacenews.feature.article.model.ArticleDetailV2

sealed interface DetailV2UiState {
    data class Idle(
        val data: ArticleDetailV2,
    ) : DetailV2UiState

    object Loading : DetailV2UiState
    object Empty : DetailV2UiState
}