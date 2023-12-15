package com.example.nutrichive.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nutrichive.data.user.UserModel
import com.example.nutrichive.utils.ResultState
import com.example.nutrichive.databinding.ActivityLoginBinding
import com.example.nutrichive.ui.customView.EditTextEmail
import com.example.nutrichive.ui.customView.MyButton
import com.example.nutrichive.ui.customView.MyEditText
import com.example.nutrichive.ui.main.MainActivity
import com.example.nutrichive.ui.profile.UserViewModelFactory
import com.example.nutrichive.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var myButton: MyButton
    private lateinit var editTextEmail: EditTextEmail
    private lateinit var editTextPassword: MyEditText

    private val viewModel by viewModels<LoginViewModel> {
        UserViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener {
            login()
        }

        myButton = binding.loginButton
        editTextEmail = binding.emailEditText
        editTextPassword = binding.passwordEditText

        editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable) {
                setMyButtonEnable()
            }
        })

        editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable) {
                setMyButtonEnable()
            }
        })

        setMyButtonEnable()
        playAnimation()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun setMyButtonEnable() {
        val email = editTextEmail.text
        val password = editTextPassword.text

        myButton.isEnabled = (password.toString().isNotEmpty()) && (email.toString().isNotEmpty())
    }

    private fun login() {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(email, password).observe(this) {result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }
                        is ResultState.Success -> {
                            showLoading(false)
                            val loginResult = result.data?.data
                            val token = loginResult?.token
                            val name = loginResult?.data?.name
                            val username = loginResult?.data?.username
                            val phoneNumber = loginResult?.data?.phoneNumber
                            val email = loginResult?.data?.email
                            viewModel.setSession(UserModel(token, name, username, phoneNumber, email, true))
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        is ResultState.Error -> {
                            showLoading(false)
                            showToast(result.error)
                        }
                    }
                }
            }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
        val tvRegister = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(100)
        val register =
            ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login,
                tvRegister,
                register
            )
            startDelay = 100
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}