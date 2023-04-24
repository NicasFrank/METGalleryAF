package com.example.metgalleryaf.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metgalleryaf.data.GalleryRepository
import com.example.metgalleryaf.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GalleryUiState(
    val searchParameters: SearchParameters,
    val matchingItems: List<Item>,
    val isLoading: Boolean = false
)

private data class GalleryViewModelState(
    val searchParameters: SearchParameters? = null,
    val matchingItems: List<Item>? = null,
    val isLoading: Boolean = false
) {

    fun toUiState(): GalleryUiState =
        GalleryUiState(
            searchParameters = searchParameters ?: SearchParameters("", false),
            matchingItems = matchingItems ?: listOf(),
            isLoading = isLoading
        )

}

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val viewModelState = MutableStateFlow(GalleryViewModelState())

    val uiState = viewModelState
        .map(GalleryViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            viewModelState.value.toUiState()
        )

    fun searchForItems() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val itemList = uiState.value.searchParameters.let {
                galleryRepository.fetchItems(it.query, it.onlyHighlights)
            }
            viewModelState.update {
                it.copy(matchingItems = itemList, isLoading = false)
            }
        }
    }

    fun onSearchInputChanged(query: String) {
        val newParameters = uiState.value.searchParameters.let {
            SearchParameters(
                query,
                it.onlyHighlights
            )
        }
        viewModelState.update {
            it.copy(searchParameters = newParameters)
        }
    }

    fun onHighlightCheck() {
        val newParameters = uiState.value.searchParameters.let {
            SearchParameters(
                it.query,
                it.onlyHighlights.not()
            )
        }
        viewModelState.update {
            it.copy(searchParameters = newParameters)
        }
    }
}

data class SearchParameters(
    val query: String,
    val onlyHighlights: Boolean
)