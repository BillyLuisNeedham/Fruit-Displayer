
package com.billyluisneedham.bbctest.source.local

import androidx.lifecycle.LiveData
import com.billyluisneedham.bbctest.models.Fruit

interface ILocalFruitDataSource {

    fun getAllFruits(): LiveData<List<Fruit>>

    suspend fun saveFruits(fruits: List<Fruit>)
}