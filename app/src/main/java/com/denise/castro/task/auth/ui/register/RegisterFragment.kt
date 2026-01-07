package com.denise.castro.task.auth.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.denise.castro.task.R
import com.denise.castro.task.databinding.FragmentRegisterBinding
import com.denise.castro.task.helper.FirebaseHelper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {
        binding.apply {
            btnCreateAccount.setOnClickListener { validateData() }
        }
    }

    private fun validateData() {
        val email = binding.edtEmail.text.toString().trim()
        val pass = binding.edtPassword.text.toString().trim()

        if (email.isNotEmpty()) {
            if (pass.isNotEmpty()) {
                binding.btnCreateAccount.setLoading()
                registerUser(email, pass)
            } else {
                Snackbar.make(requireView(), "Informe uma senha", Snackbar.LENGTH_SHORT).show()
            }
        } else {
            Snackbar.make(requireView(), "Informe um e-mail", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(requireActivity()) { task ->
                binding.btnCreateAccount.setNormal()
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    Snackbar.make(
                        binding.root,
                        FirebaseHelper.validError(task.exception),
                        Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


