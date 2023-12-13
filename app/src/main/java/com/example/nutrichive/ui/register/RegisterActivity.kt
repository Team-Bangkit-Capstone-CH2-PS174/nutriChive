package com.example.nutrichive.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrichive.utils.ResultState
import com.example.nutrichive.databinding.ActivityRegisterBinding
import com.example.nutrichive.ui.home.HomeFragment

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModel.RegisterViewModelFactory.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupView()
        playAnimation()
    }

    private fun setupView(){
        binding.registerButton.setOnClickListener{
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val resultState = viewModel.registerUser(name, email, password)
            if (!name.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                resultState.observe(this) {
                    when (it) {
                        is ResultState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is ResultState.Error -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            val error = it.error
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        }

                        is ResultState.Success -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(this, "Akun Berhasil Dibuat", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, HomeFragment::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }else{
                if (name.isNullOrEmpty())binding.nameEditText.error = "Nama tidak boleh kosong!"
                if (email.isNullOrEmpty())binding.emailEditText.error = "Email tidak boleh kosong!"
                if (password.isNullOrEmpty())binding.passwordEditText.error = "Password tidak boleh kurang dari 8 karakter!"
            }
        }
    }
    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val register = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                register
            )
            startDelay = 100
        }.start()
    }
}