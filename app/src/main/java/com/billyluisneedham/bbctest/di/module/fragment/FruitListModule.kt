package com.billyluisneedham.bbctest.di.module.fragment

import androidx.lifecycle.ViewModel
import com.billyluisneedham.bbctest.di.ViewModelKey
import com.billyluisneedham.bbctest.viewmodel.FruitListViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [FruitListModule.ViewModelModule::class])
class FruitListModule {

    @Provides
    internal fun provideSomeString(): String = "Hello World"

    @Module
    internal abstract class ViewModelModule {
        @Binds
        @IntoMap
        @ViewModelKey(FruitListViewModel::class)
        internal abstract fun bindFruitListViewModel(viewModel: FruitListViewModel): ViewModel
    }
}
