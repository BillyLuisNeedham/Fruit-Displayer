package com.billyluisneedham.bbctest.viewmodel

import androidx.lifecycle.ViewModel
import com.billyluisneedham.bbctest.repository.FruitRepository
import javax.inject.Inject

class FruitListViewModel @Inject constructor(private val fruitRepository: FruitRepository): ViewModel() {

}
