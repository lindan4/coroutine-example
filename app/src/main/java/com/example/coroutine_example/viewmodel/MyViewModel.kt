package com.example.coroutine_example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutine_example.data.Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyViewModel : ViewModel() {
    private val _data = MutableStateFlow("Click the button to fetch data")
    val data: StateFlow<String> = _data

    fun fetchData() {
        viewModelScope.launch {
            _data.value = "Loading..."
            _data.value = Repository.fetchData()
        }
    }
}