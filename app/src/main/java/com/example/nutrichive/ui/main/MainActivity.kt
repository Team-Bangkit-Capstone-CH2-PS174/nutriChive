package com.example.nutrichive.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.nutrichive.R
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.nutrichive.data.user.UserPreference
import com.example.nutrichive.databinding.ActivityMainBinding
import com.example.nutrichive.ui.camera.CameraActivity
import com.example.nutrichive.ui.home.HomeFragment
import com.example.nutrichive.ui.login.LoginActivity
import com.example.nutrichive.ui.saved.SaveFragment

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.ViewModelFactory.getInstance(this, UserPreference.getInstance(dataStore))
    }
    override fun onResume() {
        super.onResume()
        checkSessionValid()
    }

    private fun checkSessionValid() {
        viewModel.checkTokenAvailable().observe(this) {
            if (it == "null") {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                setupView("Bearer $it")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())
        binding.bottomNav.background = null

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_save -> replaceFragment(SaveFragment())
            }
            true
        }

        binding.fabCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }

    private fun replaceFragment(fragment: Fragment): Boolean {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
        return true
    }
}