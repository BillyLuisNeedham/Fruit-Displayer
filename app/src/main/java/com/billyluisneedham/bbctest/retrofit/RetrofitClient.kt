package com.billyluisneedham.bbctest.retrofit

import com.billyluisneedham.bbctest.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitClient {

    companion object {

        const val BASE_URL =
            "https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/"

        val retrofitClient: Retrofit.Builder by lazy {

            val levelType: Level
            if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
                levelType = Level.BODY else levelType = Level.NONE

            val logging = HttpLoggingInterceptor()
            logging.setLevel(levelType)

            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.addInterceptor(logging)

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())

        }

        val service: Service by lazy {
            retrofitClient
                .build()
                .create(Service::class.java)
        }
    }
}