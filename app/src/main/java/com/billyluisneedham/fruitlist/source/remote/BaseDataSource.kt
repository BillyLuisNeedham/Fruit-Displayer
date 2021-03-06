package com.billyluisneedham.fruitlist.source.remote

import android.util.Log
import com.billyluisneedham.fruitlist.utils.Resource
import retrofit2.Response

abstract class BaseDataSource {
    companion object {
        private const val REMOTE_DATA_SOURCE = "remoteDataSource"
        const val ERROR_MESSAGE = "Network call has failed because: "
    }

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.e("remoteDataSource", message)
        return Resource.error("$ERROR_MESSAGE$message")
    }
}