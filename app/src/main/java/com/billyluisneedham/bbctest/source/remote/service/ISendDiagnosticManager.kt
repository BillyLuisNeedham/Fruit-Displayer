package com.billyluisneedham.bbctest.source.remote.service

interface ISendDiagnosticManager {
    suspend fun sendDiagnostics(event: DiagnosticEvents, data: String)
}