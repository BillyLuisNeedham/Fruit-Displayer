package com.billyluisneedham.bbctest.source.remote

import com.billyluisneedham.bbctest.models.FruitListResponse

interface RemoteFruitDataSource {

    suspend fun getFruits(): FruitListResponse
}