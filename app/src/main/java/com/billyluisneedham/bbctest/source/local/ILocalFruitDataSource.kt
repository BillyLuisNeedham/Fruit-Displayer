
package com.billyluisneedham.bbctest.source.local

import com.billyluisneedham.bbctest.models.Fruit
import kotlinx.coroutines.flow.Flow

interface ILocalFruitDataSource {

    fun getAllFruits(): Flow<List<Fruit>>

    suspend fun saveFruits(fruits: List<Fruit>)

    suspend fun deleteAllFruits()
}