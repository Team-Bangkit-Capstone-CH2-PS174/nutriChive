package com.example.nutrichive.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.nutrichive.data.user.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(

    private val userPreference: UserPreference
): ViewModel() {
    fun checkTokenAvailable(): LiveData<String> {
        return userPreference.getToken().asLiveData()
    }
}