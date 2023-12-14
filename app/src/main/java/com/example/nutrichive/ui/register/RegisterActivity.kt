package com.example.nutrichive.ui.register

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
import com.example.nutrichive.utils.ResultState
import com.example.nutrichive.databinding.ActivityRegisterBinding
import com.example.nutrichive.ui.customView.EditTextEmail
import com.example.nutrichive.ui.customView.MyButton
import com.example.nutrichive.ui.customView.MyEditText
import com.example.nutrichive.ui.login.LoginActivity
import com.example.nutrichive.ui.profile.UserViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var myButton: MyButton
    private lateinit var editTextEmail: EditTextEmail
    private lateinit var editTextPassword: MyEditText

    private val viewModel by viewModels<RegisterViewModel> {
        UserViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myButton = binding.registerButton
        editTextEmail = binding.emailEditText
        editTextPassword = binding.passwordEditText

        myButton.setOnClickListener {
            register()
        }

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

        binding.apply {
            nameEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }
                override fun afterTextChanged(s: Editable) {
                    setMyButtonEnable()
                }
            })
            usernameEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }
                override fun afterTextChanged(s: Editable) {
                    setMyButtonEnable()
                }
            })
            phoneNumberEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }
                override fun afterTextChanged(s: Editable) {
                    setMyButtonEnable()
                }
            })
        }
        setMyButtonEnable()
        playAnimation()
    }

    private fun setMyButtonEnable() {
        val email = editTextEmail.text
        val password = editTextPassword.text
        val name = binding.nameEditText.text
        val username = binding.usernameEditText.text
        val phoneNumber = binding.phoneNumberEditText.text

        myButton.isEnabled = (password.toString().isNotEmpty()) && (email.toString().isNotEmpty())
                && (name.toString().isNotEmpty()) && (username.toString().isNotEmpty()) && (phoneNumber.toString().isNotEmpty())
    }

    private fun register(){
        binding.registerButton.setOnClickListener{
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val username = binding.usernameEditText.text.toString()
            val phoneNumber = binding.phoneNumberEditText.text.toString()
            viewModel.registerUser(name, email, password, username, phoneNumber).observe(this) {result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }
                        is ResultState.Success -> {
                            showLoading(false)
                            val intent = Intent(this, LoginActivity::class.java)
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
//
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
        val usernameTextView =
            ObjectAnimator.ofFloat(binding.tvUsername, View.ALPHA, 1f).setDuration(100)
        val usernameEditTextLayout =
            ObjectAnimator.ofFloat(binding.usernameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val phoneNumberTextView =
            ObjectAnimator.ofFloat(binding.tvPhoneNumber, View.ALPHA, 1f).setDuration(100)
        val phoneNumberEditTextLayout =
            ObjectAnimator.ofFloat(binding.phoneNumberEditTextLayout, View.ALPHA, 1f).setDuration(100)
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
                usernameTextView,
                usernameEditTextLayout,
                phoneNumberTextView,
                phoneNumberEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
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