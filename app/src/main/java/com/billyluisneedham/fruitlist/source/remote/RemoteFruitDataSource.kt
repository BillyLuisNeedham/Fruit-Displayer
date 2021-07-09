package com.billyluisneedham.fruitlist.source.remote

import com.billyluisneedham.fruitlist.source.remote.service.Service
import javax.inject.Inject

class RemoteFruitDataSource @Inject constructor(private val service: Service): BaseDataSource(), IRemoteFruitDataSource {

    override suspend fun getFruits() = getResult { service.getFruits() }
}