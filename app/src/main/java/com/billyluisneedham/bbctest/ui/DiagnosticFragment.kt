package com.billyluisneedham.bbctest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class DiagnosticFragment: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notifyUiDrawn()
    }

    private fun notifyUiDrawn() {
        (requireActivity() as MainActivity).onUiDrawn(System.currentTimeMillis())
    }
}