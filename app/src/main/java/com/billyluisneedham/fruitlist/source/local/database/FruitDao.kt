package com.billyluisneedham.fruitlist.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.billyluisneedham.fruitlist.models.Fruit
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveFruits(fruits: List<Fruit>)

    @Query("SELECT * FROM Fruit")
    fun getAllFruits(): Flow<List<Fruit>>

    @Query("DELETE From Fruit")
    suspend fun deleteAllFruits()
}