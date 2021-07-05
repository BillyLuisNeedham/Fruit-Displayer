package com.billyluisneedham.bbctest.source

import androidx.lifecycle.LiveData
import com.billyluisneedham.bbctest.models.Fruit
import com.billyluisneedham.bbctest.source.local.ILocalFruitDataSource
import com.billyluisneedham.bbctest.source.remote.IRemoteFruitDataSource
import com.billyluisneedham.bbctest.source.remote.service.DiagnosticEvents
import com.billyluisneedham.bbctest.source.remote.service.ISendDiagnosticManager
import com.billyluisneedham.bbctest.utils.*

class FruitRepository(
    private val localFruitDataSource: ILocalFruitDataSource,
    private val remoteFruitDataSource: IRemoteFruitDataSource,
    private val sendDiagnosticManager: ISendDiagnosticManager,
    private val dispatcherProvider: IDispatcherProvider = DefaultDispatcherProvider()
) {

    companion object {

        @Volatile
        private var INSTANCE: FruitRepository? = null

        fun getInstance(
            localFruitDataSource: ILocalFruitDataSource,
            remoteFruitDataSource: IRemoteFruitDataSource,
            sendDiagnosticManager: ISendDiagnosticManager
        ) = INSTANCE ?: synchronized(this) {

            INSTANCE ?: FruitRepository(
                localFruitDataSource = localFruitDataSource,
                remoteFruitDataSource = remoteFruitDataSource,
                sendDiagnosticManager = sendDiagnosticManager
            ).also { INSTANCE = it }
        }
    }

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
        },
        dispatcher = dispatcherProvider
    )

}