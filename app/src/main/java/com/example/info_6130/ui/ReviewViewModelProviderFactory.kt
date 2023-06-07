package com.example.info_6130.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.info_6130.repository.BaseRepository

class ReviewViewModelProviderFactory(val baseRepository: BaseRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T{
        return ReviewViewModel(baseRepository) as T
    }
}