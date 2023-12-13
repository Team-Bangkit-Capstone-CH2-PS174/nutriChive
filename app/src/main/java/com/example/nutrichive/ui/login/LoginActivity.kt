//package com.example.nutrichive.ui.login
//
//import android.animation.AnimatorSet
//import android.animation.ObjectAnimator
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Toast
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.preferencesDataStore
//import com.example.nutrichive.data.user.UserPreference
//import com.example.nutrichive.utils.ResultState
//import com.example.nutrichive.databinding.ActivityLoginBinding
//import com.example.nutrichive.ui.main.MainActivity
//import com.example.nutrichive.ui.register.RegisterActivity
//
//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
//class LoginActivity : AppCompatActivity() {
//    private val viewModel: LoginViewModel by viewModels {
//        LoginViewModel.LoginViewModelFactory.getInstance(
//            UserPreference.getInstance(dataStore)
//        )
//    }
//    private lateinit var binding: ActivityLoginBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        supportActionBar?.hide()
//
//        setupView()
//        playAnimation()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        initialCheck()
//    }
//
//    private fun initialCheck() {
//        viewModel.checkIfFirstTime().observe(this) {
//            if (it) {
//                val intent = Intent(this, MainActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
//
//                finish()
//            }
//        }
//    }
//
//    private fun setupView() {
//        binding.loginButton.setOnClickListener {
//            val email = binding.emailEditText.text.toString()
//            val password = binding.passwordEditText.text.toString()
//            val resultState = viewModel.login(email, password)
//            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
//                resultState.observe(this) {
//                    when (it) {
//                        is ResultState.Loading -> {
//                            binding.progressBar.visibility = View.VISIBLE
//                        }
//
//                        is ResultState.Error -> {
//                            binding.progressBar.visibility = View.INVISIBLE
//                            val error = it.error
//                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
//                        }
//
//                        is ResultState.Success -> {
//                            binding.progressBar.visibility = View.INVISIBLE
//                            val data = it.data
//                            viewModel.saveToken(data.loginResultState.token)
//                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
//                            Log.d("LoginActivity", "Token: ${data.loginResultState.token}")
//                            val intent = Intent(this, MainActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                        }
//                    }
//                }
//
//            } else {
//                if (email.isNullOrEmpty()) binding.emailEditText.error = "Email tidak boleh kosong!"
//                if (password.isNullOrEmpty()) binding.passwordEditText.error =
//                    "Password tidak boleh kurang dari 8 karakter!"
//            }
//
//        }
//        binding.registerButton.setOnClickListener {
//            val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
//        }
//
//    }
//
//    private fun playAnimation() {
//        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.start()
//
//        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(100)
//        val emailTextView =
//            ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(100)
//        val emailEditTextLayout =
//            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
//        val passwordTextView =
//            ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(100)
//        val passwordEditTextLayout =
//            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
//        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
//        val tvRegister = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(100)
//        val register =
//            ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(100)
//
//        AnimatorSet().apply {
//            playSequentially(
//                title,
//                emailTextView,
//                emailEditTextLayout,
//                passwordTextView,
//                passwordEditTextLayout,
//                login,
//                tvRegister,
//                register
//            )
//            startDelay = 100
//        }.start()
//    }
//}