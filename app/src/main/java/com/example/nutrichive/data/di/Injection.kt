package com.example.nutrichive.data.di

import android.content.Context
import com.example.nutrichive.data.api.ApiConfig
import com.example.nutrichive.data.repository.RecipesRepository
import com.example.nutrichive.data.user.UserPreference
import com.example.nutrichive.data.user.UserRepository
import com.example.nutrichive.data.user.dataStore

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService, userPreference)
    }
    fun provideRepository(): RecipesRepository {
        val apiService = ApiConfig.getApiService()
        return RecipesRepository.getInstance(apiService)
    }
}