package com.denise.castro.task.helper

import com.denise.castro.task.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class FirebaseHelper {

    companion object {

        fun getDataBase() = FirebaseDatabase.getInstance().reference

        private fun getAuth() = FirebaseAuth.getInstance()

        fun getIdUser() = getAuth().uid

        fun isAuthenticated() = getAuth().currentUser != null

        fun validError(exception: Exception?): Int {
            return when (exception) {
                is FirebaseAuthWeakPasswordException -> R.string.strong_password_register_fragment
                is FirebaseAuthInvalidCredentialsException -> R.string.invalid_password_register_fragment
                is FirebaseAuthInvalidUserException -> R.string.account_not_registered_register_fragment
                is FirebaseAuthUserCollisionException -> R.string.email_in_use_register_fragment
                is FirebaseAuthException -> {
                    when (exception.errorCode) {
                        "ERROR_INVALID_EMAIL" -> R.string.invalid_email_register_fragment
                        "ERROR_USER_NOT_FOUND" -> R.string.account_not_registered_register_fragment
                        "ERROR_WRONG_PASSWORD" -> R.string.invalid_password_register_fragment
                        else -> R.string.error_generic
                    }
                }
                else -> R.string.error_generic
            }
        }
    }
}