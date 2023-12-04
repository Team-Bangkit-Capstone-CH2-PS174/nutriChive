package com.example.nutrichive.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutrichive.R
import com.example.nutrichive.data.response.DataItem
import com.example.nutrichive.databinding.FragmentCategoryBinding
import com.example.nutrichive.ui.ViewModelFactory
import com.example.nutrichive.utils.ResultState

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoriUtama.isSelected = true
        updateButtonState(binding.categoriUtama)

        binding.categoriChicken.isSelected = false
        updateButtonState(binding.categoriChicken)

        binding.categoriDessert.isSelected = false
        updateButtonState(binding.categoriDessert)

        binding.categoriHealth.isSelected = false
        updateButtonState(binding.categoriHealth)

        binding.rvCategory.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

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
    }

    private fun setRecipe(item: List<DataItem>) {
        val adapter = CategoryAdapter()
        adapter.submitList(item)
        binding.rvCategory.adapter = adapter
    }

    private fun updateButtonState(button: Button) {
        if (button.isSelected) {
            val colorOnPrimary = requireContext().theme.obtainStyledAttributes(
                intArrayOf(com.google.android.material.R.attr.colorOnPrimary)
            ).getColor(0, 0)

            button.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_button) as Drawable
            button.setTextColor(colorOnPrimary)
        } else {
            val colorPrimary = requireContext().theme.obtainStyledAttributes(
                intArrayOf(com.google.android.material.R.attr.colorPrimary)
            ).getColor(0, 0)

            button.background = null
            button.setTextColor(colorPrimary)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressCategory.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}