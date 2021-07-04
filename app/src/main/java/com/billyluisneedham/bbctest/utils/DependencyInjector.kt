package com.billyluisneedham.bbctest.utils

import android.content.Context
import com.billyluisneedham.bbctest.source.FruitRepository
import com.billyluisneedham.bbctest.source.local.database.FruitDao
import com.billyluisneedham.bbctest.source.local.database.FruitDatabase
import com.billyluisneedham.bbctest.source.remote.RemoteFruitDataSource
import com.billyluisneedham.bbctest.source.remote.service.RetrofitClient
import com.billyluisneedham.bbctest.source.remote.service.SendDiagnosticManager
import com.billyluisneedham.bbctest.source.remote.service.Service

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

    private fun provideService(): Service = RetrofitClient.service

    private fun provideSendDiagnosticManager() = SendDiagnosticManager(provideService())

}