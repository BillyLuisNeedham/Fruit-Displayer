package com.billyluisneedham.bbctest.source

import com.billyluisneedham.bbctest.models.FruitResponse
import com.billyluisneedham.bbctest.source.local.LocalFruitDataSource
import com.billyluisneedham.bbctest.source.remote.RemoteFruitDataSource
import com.billyluisneedham.bbctest.source.remote.service.Service

class FruitRepository(
    private val localFruitDataSource: LocalFruitDataSource,
    private val remoteFruitDataSource: RemoteFruitDataSource
) {

    suspend fun getFruits(): List<FruitResponse> {
        return service.getFruits().fruits
    }

    companion object {

        @Volatile
        private var INSTANCE: FruitRepository? = null

        fun getInstance(service: Service) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: FruitRepository(service).also { INSTANCE = it }
        }
    }
}