package com.denise.castro.task.auth.ui.recover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.denise.castro.task.R
import com.denise.castro.task.databinding.FragmentRecoverAccountBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RecoverAccountFragment : Fragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth


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
        binding.apply {
            btnRecoverAccount.setOnClickListener { validateData() }
        }
    }

    private fun validateData() {
        val email = binding.edtEmail.text.toString().trim()

        if (email.isNotEmpty()) {
            binding.btnRecoverAccount.setLoading()
            recoverAccountUser(email)
        } else {
            Snackbar.make(requireView(), "Informe um e-mail", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun recoverAccountUser(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                binding.btnRecoverAccount.setNormal()

                if (task.isSuccessful) {
                    binding.edtEmail.text?.clear()

                    Snackbar.make(
                        requireView(),
                        "Se o e-mail estiver cadastrado, enviaremos um link de recuperação de senha.",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    binding.edtEmail.text?.clear()
                    Snackbar.make(
                        requireView(),
                        "Erro ao tentar enviar o link. Verifique o e-mail e tente novamente.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}