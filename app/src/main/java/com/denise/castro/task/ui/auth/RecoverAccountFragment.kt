package com.denise.castro.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.denise.castro.task.R
import com.denise.castro.task.databinding.FragmentRecoverAccountBinding
import com.denise.castro.task.ui.fragment.BaseFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RecoverAccountFragment : BaseFragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var  auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {
        binding.buttonRecoverAccount.setOnClickListener { validateData() }
    }

    private fun validateData() {
        val email = binding.editTextEmailRecoverAccount.text.toString().trim()

        when {
            email.isEmpty() -> showErrorMessage(binding.root, "O e-mail é obrigatório")
            else -> {
                binding.progressBar.isVisible = true
                hideKeyboard()
                recoverAccountUser(email)
            }
        }
    }

    private fun recoverAccountUser(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    showSuccessMessage(binding.root, "Pronto, acabamos de enviar um link para o seu e-mail.")
                } else {
                    binding.progressBar.isVisible = false
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}