package com.example.biteshub.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.biteshub.R
import com.example.biteshub.UserPreferences
import com.example.biteshub.databinding.ActivityLoginnBinding
import com.example.biteshub.dataclass.LoginDataAccount
import com.example.biteshub.viewmodel.DataStoreViewModel
import com.example.biteshub.viewmodel.MainViewModel
import com.example.biteshub.viewmodel.MainViewModelFactory
import com.example.biteshub.viewmodel.ViewModelFactory

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class login : AppCompatActivity() {
    private lateinit var binding : ActivityLoginnBinding

    private val loginViewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory(this))[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginnBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ifClicked()

        val preferences = UserPreferences.getInstance(dataStore)
        val dataStoreViewModel =
            ViewModelProvider(this, ViewModelFactory(preferences))[DataStoreViewModel::class.java]

        dataStoreViewModel.getLoginSession().observe(this) { sessionTrue ->
            if (sessionTrue) {
                val intent = Intent(this@login, home::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

        loginViewModel.message.observe(this) { message ->
            responseLogin(
                message,
                dataStoreViewModel
            )
        }

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun responseLogin(
        message: String,
        dataStoreViewModel: DataStoreViewModel
    ) {
        if (message.contains("Hello")) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            val user = loginViewModel.userlogin.value
            dataStoreViewModel.saveLoginSession(true)
            dataStoreViewModel.saveToken(user?.loginResult!!.token)
            dataStoreViewModel.saveName(user.loginResult.username)
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun ifClicked() {
        binding.loginButton.setOnClickListener {
            binding.CVEmail.clearFocus()
            binding.PasswordLogin.clearFocus()

            if (isDataValid()) {
                val requestLogin = LoginDataAccount(
                    binding.CVEmail.text.toString().trim(),
                    binding.PasswordLogin.text.toString().trim()
                )
                loginViewModel.login(requestLogin)
            } else {
                if (!binding.CVEmail.isEmailValid) binding.CVEmail.error =
                    getString(R.string.emailNone)
                if (!binding.PasswordLogin.isPasswordValid) binding.PasswordLogin.error =
                    getString(R.string.passwordNone)

                Toast.makeText(this, R.string.invalidLogin, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }

        binding.seePassword.setOnCheckedChangeListener { _, isChecked ->
            binding.PasswordLogin.transformationMethod = if (isChecked) {
                HideReturnsTransformationMethod.getInstance()
            } else {
                PasswordTransformationMethod.getInstance()
            }
            // Set selection to end of text
            binding.PasswordLogin.text?.let { binding.PasswordLogin.setSelection(it.length) }
        }
    }

    private fun isDataValid(): Boolean {
        return binding.CVEmail.isEmailValid && binding.PasswordLogin.isPasswordValid
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}