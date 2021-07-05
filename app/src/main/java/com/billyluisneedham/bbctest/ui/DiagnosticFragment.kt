package com.billyluisneedham.bbctest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

abstract class DiagnosticFragment : Fragment() {

    private val diagnosticViewModel: DiagnosticViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notifyUiDrawn()
    }

    private fun notifyUiDrawn() {
        diagnosticViewModel.setOnUiDrawnTimeStamp(System.currentTimeMillis())
    }
}