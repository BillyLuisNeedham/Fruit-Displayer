package com.billyluisneedham.bbctest.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.system.measureTimeMillis

fun <T, A> performGetOperation(
    databaseQuery: () -> Flow<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit,
    clearDatabaseCall: suspend () -> Unit,
    networkCallToSaveTimeMeasurement: suspend (Long) -> Unit,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): LiveData<Resource<T>> =
    liveData(dispatcher) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source.asLiveData())

        val responseWithTime = timeGetOperation { networkCall.invoke() }
        val response = responseWithTime.second
        if (response.status == Resource.Status.SUCCESS) {
            // no id from api, need to clear to prevent duplicates
            clearDatabaseCall()
            saveCallResult(response.data!!)
        } else if (response.status == Resource.Status.ERROR) {
            emit(Resource.error(response.message!!))
            emitSource(source.asLiveData())
        }

        networkCallToSaveTimeMeasurement(responseWithTime.first)
    }

suspend fun <T> timeGetOperation(networkCallToBeTimed: suspend () -> T): Pair<Long, T> {
    var result: T
    val timeInMills = measureTimeMillis {
        result = networkCallToBeTimed()
    }

    return Pair(timeInMills, result)
}