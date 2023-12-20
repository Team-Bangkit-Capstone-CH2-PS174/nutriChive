package com.example.nutrichive.ui.saved

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nutrichive.data.response.DataItem2
import com.example.nutrichive.databinding.FragmentSaveBinding
import com.example.nutrichive.ui.login.LoginActivity
import com.example.nutrichive.ui.profile.UserViewModelFactory
import com.example.nutrichive.utils.ResultState

class SaveFragment : Fragment() {
    private lateinit var binding: FragmentSaveBinding
    private val viewModel by viewModels<SaveViewModel> {
        UserViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFavRecipes.layoutManager = GridLayoutManager(requireActivity(), 2)

        showData()
    }

    override fun onResume() {
        super.onResume()
        showData()
    }

    private fun showData() {
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            } else {
                viewModel.getListFavorite(user.token).observe(viewLifecycleOwner) { resultState ->
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
        }
    }

    private fun setRecipe(item: List<DataItem2>) {
        val adapter = FavRecipeAdapter()
        adapter.submitList(item)
        binding.rvFavRecipes.adapter = adapter
        binding.imgNoData.isVisible = item.isEmpty()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}