package com.billyluisneedham.bbctest.view

import androidx.lifecycle.ViewModelProvider
import com.billyluisneedham.bbctest.viewmodel.FruitListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FruitListFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(FruitListViewModel::class.java)
    }
}