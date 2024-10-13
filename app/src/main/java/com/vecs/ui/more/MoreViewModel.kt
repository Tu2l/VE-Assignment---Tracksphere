package com.vecs.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MoreViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value =
            "This is More Fragment, due to lack of details provided with the assignment this screen is kept empty."
    }
    val text: LiveData<String> = _text
}