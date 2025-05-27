package cz.weinzettl.spacenews.feature.homepage.presentation.model

import androidx.paging.PagingData
import cz.weinzettl.spacenews.sdk.article.domain.model.Article
import kotlinx.coroutines.flow.Flow

sealed interface HomePageUiState {
    val isEnhancedDesignOn: Boolean

    data class Idle(
        val articles: Flow<PagingData<Article>>,
        override val isEnhancedDesignOn: Boolean = false
    ) : HomePageUiState

    data object Loading : HomePageUiState {
        override val isEnhancedDesignOn = false
    }

    data object Empty : HomePageUiState {
        override val isEnhancedDesignOn: Boolean = false
    }
}