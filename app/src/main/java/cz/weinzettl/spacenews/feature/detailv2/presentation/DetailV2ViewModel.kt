package cz.weinzettl.spacenews.feature.detailv2.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cz.weinzettl.spacenews.feature.detailv2.presentation.model.DetailV2UiState
import cz.weinzettl.spacenews.sdk.article.domain.GetArticleDetailV2UseCase
import cz.weinzettl.spacenews.sdk.navigation.Destination
import cz.weinzettl.spacenews.sdk.viewmodel.WhileSubscribed5000
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailV2ViewModel(
    savedStateHandle: SavedStateHandle,
    private val getArticleDetailUseCase: GetArticleDetailV2UseCase
) : ViewModel() {
    private val articleId: Int = savedStateHandle.toRoute<Destination.Detail>().articleId
    private val _uiState = MutableStateFlow<DetailV2UiState>(DetailV2UiState.Empty)
    val uiState: StateFlow<DetailV2UiState> = _uiState
        .onStart {
            loadData()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed5000(), DetailV2UiState.Empty)

    private fun loadData() {
        viewModelScope.launch {
            val detailResult = getArticleDetailUseCase(articleId)
            detailResult.fold(
                onSuccess = {
                    val newState = DetailV2UiState.Idle(it)
                    _uiState.emit(newState)
                },
                onFailure = {
                    //DetailUiState.Error(it)
                }
            )
        }
    }
}