package com.example.metgalleryaf.ui.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.metgalleryaf.data.GalleryRepository

class ItemViewModel(
    private val galleryRepository: GalleryRepository
): ViewModel() {
    companion object{
        fun provideFactory(
            galleryRepository: GalleryRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if(modelClass.isAssignableFrom(ItemViewModel::class.java)){
                    @Suppress("UNCHECKED_CAST")
                    return ItemViewModel(galleryRepository) as T
                }
                throw IllegalArgumentException("Unable to construct ViewModel")
            }
        }
    }

}