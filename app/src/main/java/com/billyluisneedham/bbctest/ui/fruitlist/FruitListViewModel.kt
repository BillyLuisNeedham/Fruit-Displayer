package com.billyluisneedham.bbctest.ui.fruitlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.billyluisneedham.bbctest.models.Fruit
import com.billyluisneedham.bbctest.source.FruitRepository

class FruitListViewModel(private val fruitRepository: FruitRepository) : ViewModel() {

    companion object {
        private const val TAG = "FruitListViewModel"
    }

    val fruitList: LiveData<List<Fruit>> = fruitRepository.getFruits().asLiveData()


    class Factory(private val fruitRepository: FruitRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FruitListViewModel(fruitRepository) as T
        }
    }

}


