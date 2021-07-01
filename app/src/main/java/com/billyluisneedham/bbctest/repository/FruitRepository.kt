package com.billyluisneedham.bbctest.repository

import com.billyluisneedham.bbctest.models.FruitResponse
import com.billyluisneedham.bbctest.retrofit.Service

class FruitRepository(private val service: Service) {

    suspend fun getFruits(): List<FruitResponse> {
        return service.getFruits()
    }

    companion object {
        @Volatile
        private var instance: FruitRepository? = null

        fun getInstance(service: Service) = instance ?: synchronized(this) {
            instance ?: FruitRepository(service).also { instance = it }
        }
    }
}