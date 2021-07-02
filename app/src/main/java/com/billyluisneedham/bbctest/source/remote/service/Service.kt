package com.billyluisneedham.bbctest.source.remote.service

import com.billyluisneedham.bbctest.models.FruitListResponse
import com.billyluisneedham.bbctest.source.remote.RemoteFruitDataSource
import retrofit2.http.GET

interface Service: RemoteFruitDataSource {

    @GET("data.json")
    override suspend fun getFruits(): FruitListResponse

}
