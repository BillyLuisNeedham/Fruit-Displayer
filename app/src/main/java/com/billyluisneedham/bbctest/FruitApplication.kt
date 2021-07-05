package com.billyluisneedham.bbctest

import android.app.Application
import android.util.Log
import com.billyluisneedham.bbctest.source.remote.service.DiagnosticEvents
import com.billyluisneedham.bbctest.source.remote.service.SendDiagnosticManager
import com.billyluisneedham.bbctest.utils.DependencyInjector
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

class FruitApplication : Application() {

    companion object {
        private const val TAG = "FruitApplication"
    }

    private lateinit var sendDiagnosticManager: SendDiagnosticManager

    override fun onCreate() {
        super.onCreate()
        sendDiagnosticManager =
            SendDiagnosticManager.newInstance(DependencyInjector.provideService())
        setupCrashHandler()
    }


    private fun setupCrashHandler() {

        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            try {
                runBlocking {

                    val exception = e.toString()

                    sendDiagnosticManager.sendDiagnostics(DiagnosticEvents.Error, exception)

                }
            } catch (e: InterruptedException) {
                Log.e(TAG, "exception: $e")
            }

            exitProcess(2)
        }


    }

}