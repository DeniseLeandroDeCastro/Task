package com.denise.castro.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.denise.castro.task.R
import com.denise.castro.task.databinding.FragmentRegisterBinding
import com.denise.castro.task.ui.fragment.BaseFragment
import com.denise.castro.task.util.clearFields
import com.denise.castro.task.util.setIcon
import com.google.android.material.snackbar.Snackbar
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

        when {
            email.isEmpty() -> showErrorMessage(binding.root, "O e-mail é obrigatório")
            password.isEmpty() -> showErrorMessage(binding.root, "A senha é obrigatória")
            password.length < 6 -> showErrorMessage(binding.root, "A senha deve ter no mínimo 6 caracteres")
            else -> {
                hideKeyboard()
                binding.progressBarRegister.isVisible = true
                registerUser(email, password)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    clearFields(binding.editTextEmailRegister, binding.editTextSenhaRegister)

                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                } else {
                    binding.progressBarRegister.isVisible = false

                    val message = when (task.exception) {
                        is com.google.firebase.auth.FirebaseAuthUserCollisionException ->
                            "Este e-mail já está cadastrado."
                        is com.google.firebase.auth.FirebaseAuthInvalidCredentialsException ->
                            "E-mail inválido."
                        else -> "Erro ao criar conta: " + task.exception?.message
                    }
                    showErrorMessage(binding.root, message)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}