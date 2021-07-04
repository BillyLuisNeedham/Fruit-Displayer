package com.billyluisneedham.bbctest.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, A> performGetOperation(
    databaseQuery: () -> Flow<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit,
    clearDatabaseCall: suspend () -> Unit
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source.asLiveData())

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            // no id from api, need to clear to prevent duplicates
            clearDatabaseCall()
            saveCallResult(responseStatus.data!!)
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source.asLiveData())
        }
    }