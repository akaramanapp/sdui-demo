package com.demo.sdui.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.sdui.data.repository.SduiRepository
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ─── Native domain models (no SDUI sealed classes, no toSduiComponent mapping) ─

data class NativeTransferPage(
    @SerializedName("page_title") val title: String,
    val favorites: List<NativeFavItem>,
    val transfers: List<NativeTxItem>
)

data class NativeFavItem(
    val id: String,
    val initials: String,
    val label: String,
    val name: String,
    val color: String
)

data class NativeTxItem(
    val id: String,
    val type: String,
    val date: String,
    val name: String,
    @SerializedName("masked_iban") val iban: String,
    val amount: String
)

// ─── ViewModel ────────────────────────────────────────────────────────────────

class NativeTransferViewModel(
    private val repository: SduiRepository = SduiRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<NativeTransferPage>>(UiState.Loading)
    val uiState: StateFlow<UiState<NativeTransferPage>> = _uiState.asStateFlow()

    init { loadPage() }

    fun loadPage() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            runCatching {
                // Same endpoint as SDUI — but we parse directly into native models,
                // skipping SduiPage → sealed class → component renderer pipeline
                val raw = repository.fetchTransferPage()

                val favs = raw.components
                    .firstOrNull { it.type == "transfer_favorites" }
                    ?.favorites
                    ?.map { NativeFavItem(it.id, it.initials ?: "", it.label, it.name, it.color) }
                    ?: emptyList()

                val txs = raw.components
                    .firstOrNull { it.type == "transfer_history" }
                    ?.transfers
                    ?.map { NativeTxItem(it.id, it.type, it.date, it.name, it.masked_iban, it.amount) }
                    ?: emptyList()

                NativeTransferPage(title = raw.page_title, favorites = favs, transfers = txs)
            }
                .onSuccess { _uiState.value = UiState.Success(it) }
                .onFailure { _uiState.value = UiState.Error(it.message ?: "Hata") }
        }
    }
}
