package cz.weinzettl.spacenews.feature.detailv2.presentation.model

import cz.weinzettl.spacenews.feature.article.model.ArticleDetailV2

sealed interface DetailV2UiState {
    val articleDetail: ArticleDetailV2?

    data class Idle(
        override val articleDetail: ArticleDetailV2,
    ) : DetailV2UiState

    object Loading : DetailV2UiState {
        override val articleDetail: ArticleDetailV2? = null
    }

    object Empty : DetailV2UiState {
        override val articleDetail: ArticleDetailV2? = null
    }
}