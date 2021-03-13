package com.si.restro.ui.entry

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.si.restro.HomeScreenActivity
import com.si.restro.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel
    private var isValidLogin: Boolean = false
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            onLoginClicked()
        }

        binding.tvSignup.setOnClickListener {
            launchSignupActivity()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        userViewModel.isValidLogin.observe(this, Observer { isValidLogin ->
            this.isValidLogin = isValidLogin

            if (isValidLogin) {
                getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                    .putBoolean("is_logged_in", true).apply()
                getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                    .putString("current_user_email", email).apply()
                Toast.makeText(baseContext, "Successfully logged in!", Toast.LENGTH_SHORT)
                    .show()

                startActivity(Intent(this, HomeScreenActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    baseContext,
                    "Invalid credentials. Please try again!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }

    private fun onLoginClicked() {
        email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()

        if (("" != email)
            && ("" != pass)
        ) {
            userViewModel.isValidLogin(email, pass)
        } else {
            Toast.makeText(
                baseContext,
                "Please enter all details...!",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun launchSignupActivity() {
        startActivity(Intent(this, SignupActivity::class.java))
        finish()
    }
}