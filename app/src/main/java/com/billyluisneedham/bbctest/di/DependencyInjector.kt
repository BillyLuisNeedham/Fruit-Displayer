package com.billyluisneedham.bbctest.di

import com.billyluisneedham.bbctest.source.FruitRepository
import com.billyluisneedham.bbctest.source.remote.service.RetrofitClient

object DependencyInjector {
    fun getFruitRepository(): FruitRepository = FruitRepository.getInstance(RetrofitClient.service)
}