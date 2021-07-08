
package com.billyluisneedham.fruitlist.source.local

import com.billyluisneedham.fruitlist.models.Fruit
import kotlinx.coroutines.flow.Flow

interface ILocalFruitDataSource {

    fun getAllFruits(): Flow<List<Fruit>>

    suspend fun saveFruits(fruits: List<Fruit>)

    suspend fun deleteAllFruits()
}