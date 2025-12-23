package com.denise.castro.task.ui.fragment

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.denise.castro.task.R
import com.denise.castro.task.util.setIcon
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    protected fun showErrorMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark))
            .setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            .setIcon(R.drawable.ic_error, android.R.color.white)
            .show()
    }

    protected fun showSuccessMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), android.R.color.holo_green_dark))
            .setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            .setIcon(R.drawable.ic_success, android.R.color.white)
            .show()
    }

    protected fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}