package com.billyluisneedham.bbctest.source.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.billyluisneedham.bbctest.models.Fruit
import com.billyluisneedham.bbctest.source.local.ILocalFruitDataSource

@Dao
interface FruitDao: ILocalFruitDataSource {

    @Insert(onConflict = REPLACE)
    override suspend fun saveFruits(fruits: List<Fruit>)

    @Query("SELECT * FROM Fruit")
    override fun getAllFruits(): LiveData<List<Fruit>>

    @Query("DELETE From Fruit")
    suspend fun deleteAll()
}