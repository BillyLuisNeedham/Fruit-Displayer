package com.billyluisneedham.bbctest.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.billyluisneedham.bbctest.models.Fruit
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(fruits: List<Fruit>)

    @Query("SELECT * FROM Fruit")
    fun getAll(): Flow<List<Fruit>>

    @Query("DELETE From Fruit")
    suspend fun deleteAll()
}