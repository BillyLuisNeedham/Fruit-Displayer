package com.billyluisneedham.bbctest.source.remote.service

import android.util.Log

class SendDiagnosticManager(private val service: Service) : ISendDiagnosticManager {

    companion object {
        private const val TAG = "SendDiagnosticManager"
    }

    override suspend fun sendDiagnostics(event: DiagnosticEvents, data: String) {
        try {
            service.sendDiagnostics(event.string, data)
        } catch (e: Exception) {
            Log.e(TAG, "exception occurred in sendDiagnostics: $e")
        }
    }
}