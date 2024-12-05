package com.example.homeguard.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.homeguard.AppDatabase
import com.example.homeguard.Recording
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.homeguard.data.PreferencesManager
import com.example.homeguard.network.RetrofitInstance
import com.example.homeguard.network.Notification
import kotlinx.coroutines.withContext


class HomeViewModel(application: Application, private val scope: CoroutineScope) : AndroidViewModel(application) {

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> get() = _notifications

    private val _isButtonGreen = MutableStateFlow(false)
    val isButtonGreen: StateFlow<Boolean> get() = _isButtonGreen

    private val _isSliderOn = MutableStateFlow(false)
    val isSliderOn: StateFlow<Boolean> get() = _isSliderOn

    private val recordingDao = AppDatabase.getDatabase(application, scope).recordingDao()
    private val preferencesManager = PreferencesManager(application)
    init {
        viewModelScope.launch {
            preferencesManager.isButtonGreen.collect { isGreen ->
                Log.d("HomeViewModel", "Retrieved button state from DataStore: $isGreen")
                _isButtonGreen.value = isGreen
            }
            preferencesManager.isSliderOn.collect { isOn ->
                Log.d("HomeViewModel", "Retrieved slider state from DataStore: $isOn")
                _isSliderOn.value = isOn
            }
        }
    }

    fun toggleButtonColor() {
        _isButtonGreen.value = !_isButtonGreen.value
        viewModelScope.launch {
            preferencesManager.saveButtonState(_isButtonGreen.value)
        }
    }

    fun toggleSlider() {
        _isSliderOn.value = !_isSliderOn.value
        viewModelScope.launch {
            preferencesManager.saveSliderState(_isSliderOn.value)
        }
    }

    fun addRecording(recording: Recording) {
        viewModelScope.launch {
            recordingDao.insertRecording(recording)
        }
    }

    fun getRecordings(onResult: (List<Recording>) -> Unit) {
        viewModelScope.launch {
            val recordings = recordingDao.getAllRecordings()
            Log.d("HomeViewModel", "Fetched Recordings: $recordings")
            onResult(recordings)
        }
    }

    fun checkDatabasePopulated() {
        viewModelScope.launch {
            val recordings = recordingDao.getAllRecordings()
            if (recordings.isNotEmpty()) {
                recordings.forEach { recording ->
                    Log.d("HomeViewModel", "Recording: $recording")
                }
            }
        }
    }
    fun fetchNotifications() {
        scope.launch {
            try {
                val response = RetrofitInstance.api.getNotifications()
                withContext(Dispatchers.Main) {
                    _notifications.value = response
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _notifications.value = emptyList()
                }
            }
        }
    }
}

class HomeViewModelFactory(
    private val application: Application,
    private val scope: CoroutineScope
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(application, scope) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}