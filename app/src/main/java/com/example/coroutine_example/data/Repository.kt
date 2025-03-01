package com.example.coroutine_example.data

import kotlinx.coroutines.delay

object Repository {
    suspend fun fetchData(): String {
        delay(2000) // Simulate network request
        return "Fetched Data Successfully!"
    }
}