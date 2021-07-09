package com.billyluisneedham.fruitlist.source

import androidx.lifecycle.LiveData
import com.billyluisneedham.fruitlist.models.Fruit
import com.billyluisneedham.fruitlist.source.local.database.FruitDao
import com.billyluisneedham.fruitlist.source.remote.IRemoteFruitDataSource
import com.billyluisneedham.fruitlist.source.remote.service.DiagnosticEvents
import com.billyluisneedham.fruitlist.source.remote.service.ISendDiagnosticManager
import com.billyluisneedham.fruitlist.utils.Resource
import com.billyluisneedham.fruitlist.utils.performGetOperation
import com.billyluisneedham.fruitlist.utils.toModel
import javax.inject.Inject

class FruitRepository @Inject constructor(
    private val localFruitDataSource: FruitDao,
    private val remoteFruitDataSource: IRemoteFruitDataSource,
    private val sendDiagnosticManager: ISendDiagnosticManager
) {

    fun getFruits(): LiveData<Resource<List<Fruit>>> = performGetOperation(
        databaseQuery = { localFruitDataSource.getAllFruits() },
        networkCall = { remoteFruitDataSource.getFruits() },
        saveCallResult = { fruitListResponse ->
            val mappedResponse = fruitListResponse.fruits.map { it.toModel() }
            localFruitDataSource.saveFruits(mappedResponse)
        },
        clearDatabaseCall = { localFruitDataSource.deleteAllFruits() },
        networkCallToSaveTimeMeasurement = {
            sendDiagnosticManager.sendDiagnostics(DiagnosticEvents.Load, it.toString())
        }
    )

}