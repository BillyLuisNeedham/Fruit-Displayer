package com.billyluisneedham.fruitlist.di

import android.content.Context
import com.billyluisneedham.fruitlist.BuildConfig
import com.billyluisneedham.fruitlist.source.FruitRepository
import com.billyluisneedham.fruitlist.source.local.database.FruitDao
import com.billyluisneedham.fruitlist.source.local.database.FruitDatabase
import com.billyluisneedham.fruitlist.source.remote.RemoteFruitDataSource
import com.billyluisneedham.fruitlist.source.remote.service.SendDiagnosticManager
import com.billyluisneedham.fruitlist.source.remote.service.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL =
        "https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val levelType: HttpLoggingInterceptor.Level =
            if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
                HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(logging)
        return okHttpClient.build()
    }

    @Provides
    fun provideService(retrofit: Retrofit): Service = retrofit.create(Service::class.java)

    @Singleton
    @Provides
    fun provideRemoteFruitDataSource(service: Service) = RemoteFruitDataSource(service)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        FruitDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun providerFruitDao(db: FruitDatabase) = db.getFruitDao()

    @Singleton
    @Provides
    fun provideSendDiagnosticManager(service: Service) = SendDiagnosticManager(service)

    @Singleton
    @Provides
    fun provideFruitRepository(
        localFruitDataSource: FruitDao,
        remoteFruitDataSource: RemoteFruitDataSource,
        sendDiagnosticManager: SendDiagnosticManager
    ) = FruitRepository(
        localFruitDataSource = localFruitDataSource,
        remoteFruitDataSource = remoteFruitDataSource,
        sendDiagnosticManager = sendDiagnosticManager
    )


}