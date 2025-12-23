package com.denise.castro.task.ui.auth

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.denise.castro.task.R
import com.denise.castro.task.databinding.FragmentLoginBinding
import com.denise.castro.task.ui.fragment.BaseFragment
import com.denise.castro.task.util.clearFields
import com.denise.castro.task.util.setIcon
import com.google.android.material.snackbar.Snackbar
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

        when {
            email.isEmpty() -> showErrorMessage(binding.root, "O e-mail é obrigatório")
            password.isEmpty() -> showErrorMessage(binding.root, "A senha é obrigatória")
            password.length < 6 -> showErrorMessage(binding.root, "A senha deve ter no mínimo 6 caracteres")
            else -> {
                hideKeyboard()
                binding.progressBarLogin.isVisible = true
                loginUser(email, password)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    clearFields(binding.editTextUserLogin, binding.editTextPasswordLogin)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    binding.progressBarLogin.isVisible = false
                    showErrorMessage(binding.root, "Erro: E-mail ou senha incorretos.")
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}