package cz.weinzettl.spacenews.feature.homepage.presentation.model

import androidx.paging.PagingData
import cz.weinzettl.spacenews.feature.article.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

sealed interface HomePageUiState {
    val isEnhancedDesignOn: Boolean

    data class Idle(
        val articles: Flow<PagingData<Article>> = emptyFlow(),
        override val isEnhancedDesignOn: Boolean = false
    ) : HomePageUiState

    data object Loading : HomePageUiState {
        override val isEnhancedDesignOn = false
    }
}