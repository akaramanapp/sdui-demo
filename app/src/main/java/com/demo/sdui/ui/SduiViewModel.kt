package com.demo.sdui.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.sdui.data.model.SduiComponent
import com.demo.sdui.data.model.toSduiComponent
import com.demo.sdui.data.repository.SduiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

data class SduiPageUiModel(
    val pageTitle: String,
    val components: List<SduiComponent>
)

class SduiViewModel(
    private val repository: SduiRepository = SduiRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<SduiPageUiModel>>(UiState.Loading)
    val uiState: StateFlow<UiState<SduiPageUiModel>> = _uiState.asStateFlow()

    init {
        loadPage()
    }

    fun loadPage() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            runCatching { repository.fetchPage() }
                .onSuccess { page ->
                    _uiState.value = UiState.Success(
                        SduiPageUiModel(
                            pageTitle = page.page_title,
                            components = page.components.map { it.toSduiComponent() }
                        )
                    )
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(error.message ?: "Bilinmeyen hata oluştu")
                }
        }
    }
}
