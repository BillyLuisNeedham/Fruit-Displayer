package com.billyluisneedham.bbctest.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.billyluisneedham.bbctest.R
import com.billyluisneedham.bbctest.source.remote.service.SendDiagnosticManager
import com.billyluisneedham.bbctest.ui.fruitlist.FruitListFragment
import com.billyluisneedham.bbctest.utils.DependencyInjector
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var sendDiagnosticManager: SendDiagnosticManager
    private val diagnosticViewModel: DiagnosticViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        sendDiagnosticManager =
            SendDiagnosticManager.newInstance(DependencyInjector.provideService())
        sendDiagnosticManager.setUiRequestTimeStamp(timeStamp = System.currentTimeMillis())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.flMainActivity, FruitListFragment()).commit()

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

    fun onUiDrawn(timeStampOfDraw: Long) {
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