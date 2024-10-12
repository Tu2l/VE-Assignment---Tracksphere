package com.vecs.ui.trace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TraceViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Trace Fragment"
    }
    val text: LiveData<String> = _text
}