package com.example.nutrichive.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.nutrichive.data.repository.RecipesRepository
import com.example.nutrichive.data.response.DetailRecipesResponse
import com.example.nutrichive.utils.ResultState

class DetailViewModel(private val recipesRepository: RecipesRepository): ViewModel() {

    fun getDetailRecipes(id: String): LiveData<ResultState<DetailRecipesResponse?>> = recipesRepository.getDetailRecipes(id)
}