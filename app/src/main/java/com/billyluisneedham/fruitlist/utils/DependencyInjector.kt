package com.billyluisneedham.fruitlist.utils

import android.content.Context
import com.billyluisneedham.fruitlist.source.FruitRepository
import com.billyluisneedham.fruitlist.source.local.database.FruitDao
import com.billyluisneedham.fruitlist.source.local.database.FruitDatabase
import com.billyluisneedham.fruitlist.source.remote.RemoteFruitDataSource
import com.billyluisneedham.fruitlist.source.remote.service.RetrofitClient
import com.billyluisneedham.fruitlist.source.remote.service.SendDiagnosticManager
import com.billyluisneedham.fruitlist.source.remote.service.Service

object DependencyInjector {

    fun provideFruitRepository(context: Context): FruitRepository = FruitRepository.getInstance(
        localFruitDataSource = provideFruitDao(context),
        remoteFruitDataSource = provideRemoteFruitDataSource(),
        sendDiagnosticManager = provideSendDiagnosticManager()
    )

    private fun provideFruitDao(context: Context): FruitDao {
        return FruitDatabase.getDatabase(context).getFruitDao()
    }

    private fun provideRemoteFruitDataSource(): RemoteFruitDataSource {
        return RemoteFruitDataSource.newInstance(provideService())
    }

    internal fun provideService(): Service = RetrofitClient.service

    private fun provideSendDiagnosticManager() = SendDiagnosticManager.newInstance(provideService())

}