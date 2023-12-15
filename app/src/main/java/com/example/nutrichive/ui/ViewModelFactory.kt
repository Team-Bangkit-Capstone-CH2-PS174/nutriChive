package com.example.nutrichive.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nutrichive.data.di.Injection
import com.example.nutrichive.data.repository.RecipesRepository
import com.example.nutrichive.ui.detail.DetailViewModel
import com.example.nutrichive.ui.home.HomeViewModel
import com.example.nutrichive.ui.reciperecomen.RecipeRecomentViewModel
import com.example.nutrichive.ui.search.SearchViewModel

class ViewModelFactory(private val recipesRepository: RecipesRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(recipesRepository) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(recipesRepository) as T
            }
            modelClass.isAssignableFrom(RecipeRecomentViewModel::class.java) -> {
                RecipeRecomentViewModel(recipesRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository())
            }.also { instance = it }
    }
}