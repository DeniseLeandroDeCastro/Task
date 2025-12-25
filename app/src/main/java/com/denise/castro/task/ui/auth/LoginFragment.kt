package com.denise.castro.task.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.denise.castro.task.R
import com.denise.castro.task.databinding.FragmentLoginBinding
import com.denise.castro.task.helper.FirebaseHelper
import com.denise.castro.task.ui.fragment.BaseFragment
import com.denise.castro.task.util.clearFields
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {
        binding.buttonLogin.setOnClickListener { validateData() }

        binding.textRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.textRecoverAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverAccountFragment)
        }
    }

    private fun validateData() {
        val email = binding.editTextUserLogin.text.toString().trim()
        val password = binding.editTextPasswordLogin.text.toString().trim()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextUserLogin.error = getString(R.string.invalid_email_register_fragment)
            return
        }
        if (password.isEmpty()) {
            binding.editTextPasswordLogin.error = getString(R.string.empty_password__register)
            return
        }

        hideKeyboard()
        binding.progressBarLogin.isVisible = true
        loginUser(email, password)
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    clearFields(binding.editTextUserLogin, binding.editTextPasswordLogin)
                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    binding.progressBarLogin.isVisible = false

                    val errorResId = FirebaseHelper.validError(task.exception)
                    showErrorMessage(binding.root, getString(errorResId))
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}