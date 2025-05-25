package cz.weinzettl.spacenews.feature.homepage.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import cz.weinzettl.spacenews.feature.homepage.domain.GetArticlesUseCase
import cz.weinzettl.spacenews.feature.homepage.model.Article
import cz.weinzettl.spacenews.feature.homepage.presentation.model.HomePageUiEvent
import cz.weinzettl.spacenews.feature.homepage.presentation.model.HomePageUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class HomePageViewModel(private val getArticlesUseCase: GetArticlesUseCase) : ViewModel() {
    private val errorFactory
        get() = ErrorMessageFactory
    private val _uiEvent = Channel<HomePageUiEvent>()
    val uiEvent: Flow<HomePageUiEvent> = _uiEvent.receiveAsFlow()
    val uiState: StateFlow<HomePageUiState> = createUiStateFlow()

    private fun createUiStateFlow(): StateFlow<HomePageUiState> = flow {
        val articleResult = getArticlesUseCase()
        articleResult.fold(
            onSuccess = { articlesFlow ->
                handleArticleSuccess(articlesFlow)
            },
            onFailure = { throwable ->
                handleArticleFailure(throwable)
            }
        )
    }.onStart { emit(HomePageUiState.Loading) }
        .stateIn(viewModelScope, SharingStarted.Lazily, HomePageUiState.Idle())

    private suspend fun FlowCollector<HomePageUiState>.handleArticleSuccess(articlesFlow: Flow<PagingData<Article>>) {
        val cachedArticlesFlow = articlesFlow.cachedIn(viewModelScope)
        emit(HomePageUiState.Idle(cachedArticlesFlow))
    }

    private suspend fun FlowCollector<HomePageUiState>.handleArticleFailure(throwable: Throwable) {
        _uiEvent.send(HomePageUiEvent.Error(errorFactory.getRes(throwable)))
        emit(HomePageUiState.Idle())
    }

    fun refresh() {
        val state = uiState.value
        if (state is HomePageUiState.Idle) {

        }
    }
}
