package com.example.screen.ui.main.ui.gastos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GastosViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Gastos Fragment"
    }
    val text: LiveData<String> = _text
}