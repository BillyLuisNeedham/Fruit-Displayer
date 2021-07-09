package com.billyluisneedham.fruitlist.source.remote.service

import android.util.Log
import javax.inject.Inject

class SendDiagnosticManager@Inject constructor(private val service: Service) : ISendDiagnosticManager {

    companion object {
        private const val TAG = "SendDiagnosticManager"
    }

    private var uiRequestTimeStamp: Long? = null

    fun setUiRequestTimeStamp(timeStamp: Long) {
        uiRequestTimeStamp = timeStamp
    }

    suspend fun onUiDrawCompleteSendDiagnostics(timeStampOfDrawComplete: Long) {
        if (uiRequestTimeStamp == null) throw IllegalStateException("uiRequestTimeStamp is null and should not be")
        val timeDifference = timeStampOfDrawComplete - uiRequestTimeStamp!!

        sendDiagnostics(DiagnosticEvents.Display, timeDifference.toString())
    }

    override suspend fun sendDiagnostics(event: DiagnosticEvents, data: String) {
        try {
            service.sendDiagnostics(event.string, data)
        } catch (e: Exception) {
            Log.e(TAG, "exception occurred in sendDiagnostics: $e")
        }
    }
}