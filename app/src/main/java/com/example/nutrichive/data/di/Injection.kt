package com.example.nutrichive.data.di

import android.content.Context
import com.example.nutrichive.data.api.ApiConfig
import com.example.nutrichive.data.repository.RecipesRepository

object Injection {
    fun provideRepository(): RecipesRepository {
        val apiService = ApiConfig.getApiService()
        return RecipesRepository.getInstance(apiService)
    }
}