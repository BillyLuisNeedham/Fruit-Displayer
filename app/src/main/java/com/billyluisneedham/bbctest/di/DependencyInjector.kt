package com.billyluisneedham.bbctest.di

import com.billyluisneedham.bbctest.repository.FruitRepository
import com.billyluisneedham.bbctest.retrofit.RetrofitClient

object DependencyInjector {
    fun getFruitRepository(): FruitRepository = FruitRepository.getInstance(RetrofitClient.service)
}