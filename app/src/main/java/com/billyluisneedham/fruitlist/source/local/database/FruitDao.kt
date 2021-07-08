package com.billyluisneedham.fruitlist.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.billyluisneedham.fruitlist.models.Fruit
import com.billyluisneedham.fruitlist.source.local.ILocalFruitDataSource
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitDao: ILocalFruitDataSource {

    @Insert(onConflict = REPLACE)
    override suspend fun saveFruits(fruits: List<Fruit>)

    @Query("SELECT * FROM Fruit")
    override fun getAllFruits(): Flow<List<Fruit>>

    @Query("DELETE From Fruit")
    override suspend fun deleteAllFruits()
}