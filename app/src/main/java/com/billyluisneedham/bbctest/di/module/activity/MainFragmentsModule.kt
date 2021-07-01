package com.billyluisneedham.bbctest.di.module.activity

import com.billyluisneedham.bbctest.di.module.fragment.FruitListModule
import com.billyluisneedham.bbctest.view.FruitListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentsModule {

    @ContributesAndroidInjector(modules = [FruitListModule::class])
    abstract fun contributeFruitListFragment(): FruitListFragment

}
