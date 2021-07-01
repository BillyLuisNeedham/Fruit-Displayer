package com.billyluisneedham.bbctest.di

import android.app.Application
import com.billyluisneedham.bbctest.FruitApplication
import com.billyluisneedham.bbctest.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent: AndroidInjector<FruitApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

}