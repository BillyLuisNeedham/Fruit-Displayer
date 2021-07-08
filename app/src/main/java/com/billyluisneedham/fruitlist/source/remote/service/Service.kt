package com.billyluisneedham.fruitlist.source.remote.service

import com.billyluisneedham.fruitlist.models.FruitListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

enum class DiagnosticEvents(val string: String) {
    Load("load"),
    Display("display"),
    Error("error")
}

interface Service {

    @GET("data.json")
    suspend fun getFruits(): Response<FruitListResponse>

    @GET("stats")
    suspend fun sendDiagnostics(
        @Query("event") event: String,
        @Query("data") data: String
    )

}
