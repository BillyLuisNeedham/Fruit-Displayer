package com.billyluisneedham.fruitlist.ui.fruitlist

import androidx.lifecycle.*
import com.billyluisneedham.fruitlist.models.Fruit
import com.billyluisneedham.fruitlist.source.FruitRepository
import com.billyluisneedham.fruitlist.utils.Resource

class FruitListViewModel(private val fruitRepository: FruitRepository) : ViewModel() {

    private val loadTrigger = MutableLiveData(Unit)

    var fruitList: LiveData<Resource<List<Fruit>>> = loadTrigger.switchMap {
        fruitRepository.getFruits()
    }

    fun refreshFruits() {
        loadTrigger.value = Unit
    }

    class Factory(private val fruitRepository: FruitRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FruitListViewModel(fruitRepository) as T
        }
    }

}


