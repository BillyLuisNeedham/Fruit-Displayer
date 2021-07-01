package com.billyluisneedham.bbctest.retrofit

import com.billyluisneedham.bbctest.models.FruitResponse
import retrofit2.http.GET

interface Service {

    @GET("data.json")
    suspend fun getFruits(): List<FruitResponse>

}
