package cz.weinzettl.spacenews.feature.homepage.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cz.weinzettl.spacenews.feature.homepage.domain.GetIsEnhancedDesignOnUseCase
import cz.weinzettl.spacenews.feature.homepage.domain.SetIsEnhancedDesignOnUseCase
import cz.weinzettl.spacenews.feature.homepage.presentation.model.HomePageUiEvent
import cz.weinzettl.spacenews.feature.homepage.presentation.model.HomePageUiState
import cz.weinzettl.spacenews.sdk.article.domain.GetArticlesUseCase
import cz.weinzettl.spacenews.sdk.article.domain.model.Article
import cz.weinzettl.spacenews.sdk.concurency.Dispatchers
import cz.weinzettl.spacenews.sdk.viewmodel.WhileSubscribed5000
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getEnhancedDesignUseCase: GetIsEnhancedDesignOnUseCase,
    private val setIsEnhancedDesignOnUseCase: SetIsEnhancedDesignOnUseCase,
    private val dispatchers: Dispatchers,
) : ViewModel() {
    private val errorFactory
        get() = ErrorMessageFactory
    private val _uiEvent = Channel<HomePageUiEvent>()
    val uiEvent: Flow<HomePageUiEvent> = _uiEvent.receiveAsFlow()
    val uiState: StateFlow<HomePageUiState> = createUiStateFlow()

    private fun createUiStateFlow(): StateFlow<HomePageUiState> = createHomePageStateFlow()
        .onStart {
            emit(HomePageUiState.Loading)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed5000(), HomePageUiState.Empty)

    private fun createHomePageStateFlow(): Flow<HomePageUiState> =
        flow {
            val articleResult = getArticlesUseCase()
            articleResult.fold(
                onSuccess = { articlesFlow ->
                    handleArticleSuccess(articlesFlow)
                },
                onFailure = { throwable ->
                    handleArticleFailure(throwable)
                }
            )
        }.combine(
            getEnhancedDesignUseCase().getOrNull() ?: flowOf(false)
        ) { homePageState, isEnabled ->
            if (homePageState is HomePageUiState.Idle) {
                homePageState.copy(isEnhancedDesignOn = isEnabled)
            } else {
                homePageState
            }
        }.flowOn(dispatchers.io)

    private suspend fun FlowCollector<HomePageUiState>.handleArticleSuccess(articlesFlow: Flow<PagingData<Article>>) {
        val cachedArticlesFlow = articlesFlow.cachedIn(viewModelScope)
        emit(HomePageUiState.Idle(cachedArticlesFlow))
    }

    private suspend fun FlowCollector<HomePageUiState>.handleArticleFailure(throwable: Throwable) {
        _uiEvent.send(HomePageUiEvent.Error(errorFactory.getRes(throwable)))
        emit(HomePageUiState.Empty)
    }

    fun setEnhancedDesignEnabled(enabled: Boolean) {
        viewModelScope.launch {
            setIsEnhancedDesignOnUseCase(enabled)
        }
    }
}
