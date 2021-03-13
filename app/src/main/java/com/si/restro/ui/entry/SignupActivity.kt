package com.si.restro.ui.entry

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.si.restro.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        binding.btnCreate.setOnClickListener {
            onCreateAccountClicked()
        }

        binding.tvLogin.setOnClickListener {
            launchLoginActivity()
        }
    }


    private fun onCreateAccountClicked() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val pass1 = binding.etPassword.text.toString()
        val pass2 = binding.etConfirmPassword.text.toString()

        if (("" != name)
            && ("" != email)
            && ("" != pass1)
            && ("" != pass2)
            && (pass1 == pass2)
        ) {
            userViewModel.createUser(name, email, pass1)
            Toast.makeText(
                baseContext,
                "Successfully created an account! \nPlease log in...",
                Toast.LENGTH_SHORT
            )
                .show()
            launchLoginActivity()
        } else {
            Toast.makeText(
                baseContext,
                "Please enter and verify all details...!",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun launchLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}