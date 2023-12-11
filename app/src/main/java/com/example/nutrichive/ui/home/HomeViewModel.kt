package com.example.nutrichive.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.nutrichive.data.repository.RecipesRepository
import com.example.nutrichive.data.response.DataItem
import com.example.nutrichive.utils.ResultState

class HomeViewModel(private val recipesRepository: RecipesRepository): ViewModel() {

    fun getAllRecipes(): LiveData<ResultState<List<DataItem>?>> = recipesRepository.getAllRecipes()

    fun getRandomRecipes(): LiveData<ResultState<List<DataItem>?>> = recipesRepository.getRandomRecipes()
}