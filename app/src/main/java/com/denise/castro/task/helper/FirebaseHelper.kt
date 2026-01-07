package com.denise.castro.task.helper

import com.denise.castro.task.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {

    companion object {
        fun getDataBase() = FirebaseDatabase.getInstance().reference

        private fun getAuth() = FirebaseAuth.getInstance()

        fun getIdUser() = getAuth().uid

        fun isAutenticated() = getAuth().currentUser != null

        fun validError(exception: Exception?): Int {
            return when (exception) {
                is FirebaseAuthInvalidUserException -> {
                    R.string.invalid_email_pass_register_fragment
                }

                is FirebaseAuthInvalidCredentialsException -> {
                    R.string.invalid_email_pass_register_fragment
                }

                is FirebaseAuthUserCollisionException -> {
                    R.string.email_in_use_register_fragment
                }

                is FirebaseAuthWeakPasswordException -> {
                    R.string.strong_password_register_fragment
                }

                else -> {
                    R.string.error_generic
                }
            }
        }
    }
}