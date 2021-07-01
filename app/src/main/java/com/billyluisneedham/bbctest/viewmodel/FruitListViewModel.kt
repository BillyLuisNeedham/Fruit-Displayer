package com.billyluisneedham.bbctest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.billyluisneedham.bbctest.repository.FruitRepository

class FruitListViewModel(private val fruitRepository: FruitRepository) : ViewModel() {

    companion object {
        private const val TAG = "FruitListViewModel"
    }



    class Factory(private val fruitRepository: FruitRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FruitListViewModel(fruitRepository) as T
        }
    }

}
