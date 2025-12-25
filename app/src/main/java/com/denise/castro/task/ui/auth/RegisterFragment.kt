package com.denise.castro.task.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.denise.castro.task.R
import com.denise.castro.task.databinding.FragmentRegisterBinding
import com.denise.castro.task.helper.FirebaseHelper
import com.denise.castro.task.ui.fragment.BaseFragment
import com.denise.castro.task.util.clearFields
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterFragment : BaseFragment() { // Herda da sua BaseFragment

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {
        binding.buttonRegister.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.editTextEmailRegister.text.toString().trim()
        val password = binding.editTextSenhaRegister.text.toString().trim()

        if (email.isEmpty()) {
            binding.editTextEmailRegister.error = getString(R.string.invalid_email_register_fragment)
            return
        }
        if (password.isEmpty()) {
            binding.editTextSenhaRegister.error = getString(R.string.error_generic)
            return
        }
        if (password.length < 6) {
            binding.editTextSenhaRegister.error = getString(R.string.strong_password_register_fragment)
            return
        }

        hideKeyboard()
        binding.progressBarRegister.isVisible = true
        registerUser(email, password)
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    clearFields(binding.editTextEmailRegister, binding.editTextSenhaRegister)

                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    binding.progressBarRegister.isVisible = false
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