package com.billyluisneedham.fruitlist.ui.fruitlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.billyluisneedham.fruitlist.models.Fruit
import com.billyluisneedham.fruitlist.source.FruitRepository
import com.billyluisneedham.fruitlist.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FruitListViewModel @Inject constructor(private val fruitRepository: FruitRepository) : ViewModel() {

    private val loadTrigger = MutableLiveData(Unit)

    var fruitList: LiveData<Resource<List<Fruit>>> = loadTrigger.switchMap {
        fruitRepository.getFruits()
    }

    fun refreshFruits() {
        loadTrigger.value = Unit
    }

}


