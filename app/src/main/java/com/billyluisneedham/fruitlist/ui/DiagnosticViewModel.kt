package com.billyluisneedham.fruitlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiagnosticViewModel : ViewModel() {

    private val _onUiDrawnTimeStamp = MutableLiveData<Long>()
    val onUiDrawnTimeStamp: LiveData<Long>
        get() = _onUiDrawnTimeStamp

    fun setOnUiDrawnTimeStamp(timeStamp: Long) {
        _onUiDrawnTimeStamp.value = timeStamp
    }
}
