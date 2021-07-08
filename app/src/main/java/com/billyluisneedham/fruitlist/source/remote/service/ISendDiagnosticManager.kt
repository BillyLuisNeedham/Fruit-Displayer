package com.billyluisneedham.fruitlist.source.remote.service

interface ISendDiagnosticManager {
    suspend fun sendDiagnostics(event: DiagnosticEvents, data: String)
}