package com.billyluisneedham.bbctest.source.remote

import com.billyluisneedham.bbctest.source.remote.service.Service

class RemoteFruitDataSource(private val service: Service): BaseDataSource(), IRemoteFruitDataSource {

    companion object {
        fun newInstance(service: Service) = RemoteFruitDataSource(service)
    }

    override suspend fun getFruits() = getResult { service.getFruits() }
}