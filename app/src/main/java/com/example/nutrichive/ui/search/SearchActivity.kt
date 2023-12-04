package com.example.nutrichive.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nutrichive.data.response.DataItem
import com.example.nutrichive.databinding.ActivitySearchBinding
import com.example.nutrichive.ui.ViewModelFactory
import com.example.nutrichive.ui.home.RecipeAdapter
import com.example.nutrichive.utils.ResultState

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewmodel by viewModels<SearchViewModel> {
        ViewModelFactory.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.rvAllRecipes.layoutManager = GridLayoutManager(this, 2)
        binding.searchBar.requestFocus()
        binding.searchBar.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewmodel.searchRecipes(binding.searchBar.text.toString()).observe(this@SearchActivity) { resultState ->
                    when (resultState) {
                        is ResultState.Success ->{
                            showLoading(false)
                            val recipeResponse = resultState.data
                            if (recipeResponse != null) {
                                setRecipe(recipeResponse)
                            }
                        }
                        is ResultState.Error -> {
                            showLoading(false)
                        }
                        is ResultState.Loading -> {
                            showLoading(true)
                        }
                    }
                }
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setRecipe(item: List<DataItem>) {
        val adapter = RecipeAdapter()
        adapter.submitList(item)
        binding.rvAllRecipes.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}