package com.billyluisneedham.fruitlist

import android.app.Application
import android.util.Log
import com.billyluisneedham.fruitlist.source.remote.service.DiagnosticEvents
import com.billyluisneedham.fruitlist.source.remote.service.SendDiagnosticManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.system.exitProcess

@HiltAndroidApp
class FruitApplication : Application() {

    companion object {
        private const val TAG = "FruitApplication"
    }

    @Inject
    lateinit var sendDiagnosticManager: SendDiagnosticManager

    override fun onCreate() {
        super.onCreate()
        setUiRequestTimeAsNowInSendDiagnosticManager()
        setupCrashHandler()
    }

    private fun setUiRequestTimeAsNowInSendDiagnosticManager() {
        sendDiagnosticManager.setUiRequestTimeStamp(timeStamp = System.currentTimeMillis())
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