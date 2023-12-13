package com.example.nutrichive.ui.reciperecomen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.nutrichive.data.repository.RecipesRepository
import com.example.nutrichive.data.response.DataItem
import com.example.nutrichive.utils.ResultState

class RecipeRecomentViewModel(private val recipesRepository: RecipesRepository): ViewModel() {

    fun recipeRecomen(keyword: String): LiveData<ResultState<List<DataItem>?>> = recipesRepository.searchRecipes(keyword)

}