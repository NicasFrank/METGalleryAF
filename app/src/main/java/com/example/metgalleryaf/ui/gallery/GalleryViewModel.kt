package com.example.metgalleryaf.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.metgalleryaf.data.GalleryRepository
import com.example.metgalleryaf.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GalleryUiState(
    val query: String,
    val matchingItems: List<Item>,
    val isLoading: Boolean = false
)

private data class GalleryViewModelState(
    val query: String? = null,
    val matchingItems: List<Item>? = null,
    val isLoading: Boolean = false
){

    fun toUiState(): GalleryUiState =
        GalleryUiState(
            query = query ?: "",
            matchingItems = matchingItems ?: listOf(),
            isLoading = isLoading
        )

}


class GalleryViewModel(
    private val galleryRepository: GalleryRepository
): ViewModel() {

    private val viewModelState = MutableStateFlow(GalleryViewModelState())

    val uiState = viewModelState
        .map(GalleryViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun searchForItems(query: String){
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = galleryRepository.findItems(query)
            viewModelState.update {
                if(result.isSuccess){
                    it.copy(matchingItems = result.getOrNull(), isLoading = false)
                }
                else{
                    it.copy(matchingItems = listOf(), isLoading = false)
                }
            }
        }

    }

    fun onSearchInputChanged(query: String){
        viewModelState.update {
            it.copy(query = query)
        }
    }

    companion object{
        fun provideFactory(
            galleryRepository: GalleryRepository
        ): ViewModelProvider.Factory = object :ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if(modelClass.isAssignableFrom(GalleryViewModel::class.java)){
                    @Suppress("UNCHECKED_CAST")
                    return GalleryViewModel(galleryRepository) as T
                }
                throw IllegalArgumentException("Unable to construct ViewModel")
            }
        }
    }
}