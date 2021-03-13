package com.si.restro.ui.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.si.restro.ui.entry.LoginActivity
import com.si.restro.databinding.FragmentProfileBinding
import com.si.restro.ui.entry.UserViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this).get(UserViewModel::class.java)

        val email = binding.root.context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            .getString("current_user_email", "")

        if (email != null) {
            viewModel.getUser(email)
        } else {
            Toast.makeText(binding.root.context, "Error finding details..!", Toast.LENGTH_SHORT)
                .show()
        }

        binding.btnLogout.setOnClickListener {
            activity?.applicationContext?.let { it1 -> onLogoutClicked(it1) }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.currentUser.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                binding.user = user
            }
        })
    }

    private fun onLogoutClicked(context: Context) {
        context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
            .putBoolean("is_logged_in", false).apply()

        Toast.makeText(context, "Logging out...", Toast.LENGTH_SHORT).show()

        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}