package com.billyluisneedham.bbctest.retrofit

import com.billyluisneedham.bbctest.models.FruitListResponse
import retrofit2.http.GET

interface Service {

    @GET("data.json")
    suspend fun getFruits(): FruitListResponse

}
