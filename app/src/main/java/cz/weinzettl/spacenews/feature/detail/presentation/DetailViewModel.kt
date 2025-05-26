package cz.weinzettl.spacenews.feature.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cz.weinzettl.spacenews.feature.article.domain.GetArticleDetailUseCase
import cz.weinzettl.spacenews.feature.detail.presentation.model.DetailUiState
import cz.weinzettl.spacenews.sdk.navigation.Destination
import cz.weinzettl.spacenews.sdk.viewmodel.extension.WhileSubscribed5000
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getArticleDetailUseCase: GetArticleDetailUseCase
) : ViewModel() {
    private val articleId: Int = savedStateHandle.toRoute<Destination.Detail>().articleId
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Empty)
    val uiState: StateFlow<DetailUiState> = _uiState
        .onStart {
            loadData()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed5000(), DetailUiState.Empty)

    private fun loadData() {
        viewModelScope.launch {
            val detailResult = getArticleDetailUseCase(articleId)
            detailResult.fold(
                onSuccess = {
                    val newState = DetailUiState.Idle(it.url)
                    _uiState.emit(newState)
                },
                onFailure = {
                    //DetailUiState.Error(it)
                }
            )
        }
    }
}