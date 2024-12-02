package com.example.homeguard.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _isButtonGreen = MutableStateFlow(false)
    val isButtonGreen: StateFlow<Boolean> get() = _isButtonGreen

    fun toggleButtonColor() {
        _isButtonGreen.value = !_isButtonGreen.value
        Log.d("HomeViewModel", "toggleButtonColor called. New state: ${_isButtonGreen.value}")
    }
}
