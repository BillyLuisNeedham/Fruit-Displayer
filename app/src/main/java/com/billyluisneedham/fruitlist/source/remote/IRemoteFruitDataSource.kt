package com.billyluisneedham.fruitlist.source.remote

import com.billyluisneedham.fruitlist.models.FruitListResponse
import com.billyluisneedham.fruitlist.utils.Resource

interface IRemoteFruitDataSource {

    suspend fun getFruits(): Resource<FruitListResponse>
}