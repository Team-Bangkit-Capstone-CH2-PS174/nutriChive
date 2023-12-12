package com.example.nutrichive.data.di

import android.content.Context
import com.example.nutrichive.data.api.ApiConfig
import com.example.nutrichive.data.repository.RecipesRepository
import com.example.nutrichive.data.user.UserRepository

object Injection {
    fun provideUserRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
    fun provideRepository(): RecipesRepository {
        val apiService = ApiConfig.getApiService()
        return RecipesRepository.getInstance(apiService)
    }
}