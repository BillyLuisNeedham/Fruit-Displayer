package com.billyluisneedham.bbctest.source.remote.service

import com.billyluisneedham.bbctest.models.FruitListResponse
import retrofit2.http.GET

interface Service {

    @GET("data.json")
    suspend fun getFruits(): FruitListResponse

}
