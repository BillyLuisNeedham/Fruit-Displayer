package com.billyluisneedham.bbctest.source

import com.billyluisneedham.bbctest.models.Fruit
import com.billyluisneedham.bbctest.source.local.LocalFruitDataSource
import com.billyluisneedham.bbctest.source.remote.RemoteFruitDataSource
import com.billyluisneedham.bbctest.utils.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class FruitRepository(
    private val localFruitDataSource: LocalFruitDataSource,
    private val remoteFruitDataSource: RemoteFruitDataSource
) {

    companion object {

        @Volatile
        private var INSTANCE: FruitRepository? = null

        fun getInstance(
            localFruitDataSource: LocalFruitDataSource,
            remoteFruitDataSource: RemoteFruitDataSource
        ) = INSTANCE ?: synchronized(this) {

            INSTANCE ?: FruitRepository(
                localFruitDataSource = localFruitDataSource,
                remoteFruitDataSource = remoteFruitDataSource
            ).also { INSTANCE = it }
        }
    }

    fun getFruits(): Flow<List<Fruit>> = localFruitDataSource.getAllFruits().onStart {
        refreshFruits()
    }

    private suspend fun refreshFruits() {
        val response = remoteFruitDataSource.getFruits()
        val mappedFruits = response.fruits.map { fruitResponse ->
            fruitResponse.toModel()
        }
        localFruitDataSource.saveFruits(mappedFruits)
    }

}