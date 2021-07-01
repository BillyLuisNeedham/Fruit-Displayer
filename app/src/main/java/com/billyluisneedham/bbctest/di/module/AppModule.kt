package com.billyluisneedham.bbctest.di.module

import com.billyluisneedham.bbctest.di.module.activity.ActivityBuildersModule
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule

@Module(
    includes = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        ViewModelFactoryModule::class
    ]
)
class AppModule