package com.billyluisneedham.fruitlist.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.billyluisneedham.fruitlist.R
import com.billyluisneedham.fruitlist.source.remote.service.SendDiagnosticManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    @Inject
    lateinit var sendDiagnosticManager: SendDiagnosticManager
    private val diagnosticViewModel: DiagnosticViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeOnUiDrawn()
    }

    private fun observeOnUiDrawn() {
        diagnosticViewModel.onUiDrawnTimeStamp.observe(this, {
            onUiDrawn(it)
        })
    }

    override fun onBackPressed() {
        sendDiagnosticManager.setUiRequestTimeStamp(System.currentTimeMillis())
        super.onBackPressed()
    }

    private fun onUiDrawn(timeStampOfDraw: Long) {
        lifecycleScope.launch {
            try {
                sendDiagnosticManager.onUiDrawCompleteSendDiagnostics(timeStampOfDraw)
            } catch (e: Exception) {
                Log.d(TAG, "error in onUiDrawn")
            }
        }
    }

    fun onRequestChangeOfUi(timeStampOfRequest: Long) {
        sendDiagnosticManager.setUiRequestTimeStamp(timeStampOfRequest)
    }

}