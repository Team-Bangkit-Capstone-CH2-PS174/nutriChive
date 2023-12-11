package com.example.nutrichive.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.example.nutrichive.data.api.ApiService
import com.example.nutrichive.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesRepository(private  val apiService: ApiService) {

    fun getAllRecipes() = liveData {
        emit(ResultState.Loading)
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.getAllRecipes().execute()
            }
            if (response.isSuccessful){
                emit(ResultState.Success(response.body()?.data))
            } else {
                emit(ResultState.Error("Data Gagal Dimuat"))
                Log.d("All Recipes", "onFailure: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.d("All Recipes", "onFailure: ${e.message}")
        }
    }

    fun getRandomRecipes() = liveData {
        emit(ResultState.Loading)
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.getAllRecipes().execute()
            }
            if (response.isSuccessful){
                emit(ResultState.Success(response.body()?.data?.shuffled()?.take(5)))
            } else {
                emit(ResultState.Error("Data Gagal Dimuat"))
                Log.d("Random Recipes", "onFailure: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.d("Random Recipes", "onFailure: ${e.message}")
        }
    }

    fun getDetailRecipes(id: String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.getDetailRecipes(id).execute()
            }
            if (response.isSuccessful) {
                emit(ResultState.Success(response.body()))
            } else {
                emit(ResultState.Error("Data Gagal Dimuat"))
                Log.d("Detail Recipes", "onFailure: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.d("Detail Recipes", "onFailure: ${e.message}")
        }
    }

    fun searchRecipes(keyword: String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.searchRecipes(keyword).execute()
            }
            if (response.isSuccessful) {
                emit(ResultState.Success(response.body()?.data))
            } else {
                emit(ResultState.Error("Data Gagal Dimuat"))
                Log.d("Detail Recipes", "onFailure: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.d("Detail Recipes", "onFailure: ${e.message}")
        }
    }

    companion object {
        @Volatile
        private var instance: RecipesRepository? = null
        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: RecipesRepository(apiService)
            }.also { instance = it }
    }
}