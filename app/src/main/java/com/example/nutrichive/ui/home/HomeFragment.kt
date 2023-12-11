package com.example.nutrichive.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutrichive.data.response.DataItem
import com.example.nutrichive.databinding.FragmentHomeBinding
import com.example.nutrichive.ui.ViewModelFactory
import com.example.nutrichive.ui.search.SearchActivity
import com.example.nutrichive.utils.ResultState

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBar.setOnClickListener {
            val intent = Intent(requireActivity(), SearchActivity::class.java)
            startActivity(intent)
        }

        binding.rvWesternFood.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecommedFood.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvAllRecipes.layoutManager = GridLayoutManager(requireActivity(), 2)

        homeViewModel.getAllRecipes().observe(viewLifecycleOwner) { resultState ->
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

        homeViewModel.getRandomRecipes().observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is ResultState.Success ->{
                    showLoading(false)
                    val recipeResponse = resultState.data
                    if (recipeResponse != null) {
                        setRandomRecipe(recipeResponse)
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
    }

    private fun setRecipe(item: List<DataItem>) {
        val adapter = RecipeAdapter()
        adapter.submitList(item)
        binding.rvAllRecipes.adapter = adapter
    }

    private fun setRandomRecipe(item: List<DataItem>) {
        val adapter = RecipeAdapter()
        adapter.submitList(item)
        binding.rvWesternFood.adapter = adapter
        binding.rvRecommedFood.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.progressRecomen.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.progressWestern.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}