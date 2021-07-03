package com.billyluisneedham.bbctest.ui.fruitlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.billyluisneedham.bbctest.models.Fruit
import com.billyluisneedham.bbctest.source.FruitRepository
import com.billyluisneedham.bbctest.utils.Resource

class FruitListViewModel(fruitRepository: FruitRepository) : ViewModel() {

    val fruitList: LiveData<Resource<List<Fruit>>> = fruitRepository.getFruits()

    class Factory(private val fruitRepository: FruitRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FruitListViewModel(fruitRepository) as T
        }
    }

}


