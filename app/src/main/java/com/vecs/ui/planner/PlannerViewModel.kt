package com.vecs.ui.planner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlannerViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Planner Fragment, due to lack of details provided with the assignment this screen is kept empty."
    }
    val text: LiveData<String> = _text
}