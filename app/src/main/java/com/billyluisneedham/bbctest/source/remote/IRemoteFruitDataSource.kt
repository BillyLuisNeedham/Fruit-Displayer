package com.billyluisneedham.bbctest.source.remote

import com.billyluisneedham.bbctest.models.FruitListResponse
import com.billyluisneedham.bbctest.utils.Resource

interface IRemoteFruitDataSource {

    suspend fun getFruits(): Resource<FruitListResponse>
}