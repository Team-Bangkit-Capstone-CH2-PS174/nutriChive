package com.example.nutrichive.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.nutrichive.databinding.FragmentProfileBinding
import com.example.nutrichive.ui.login.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel> {
        UserViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            }
            binding.nameUser.text = user.name
            binding.usernameUser.text = user.username
            binding.emailUser.text = user.email
            binding.phoneUser.text = user.phoneNumber
        }
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }
}