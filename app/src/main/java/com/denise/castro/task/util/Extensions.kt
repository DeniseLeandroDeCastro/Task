package com.denise.castro.task.util

import android.view.Gravity
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import android.widget.EditText

fun Snackbar.setIcon(drawableRes: Int, colorRes: Int): Snackbar {
    val textView = this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    val drawable = ContextCompat.getDrawable(context, drawableRes)

    drawable?.let {
        val sizeInPx = 28.toPx(context)
        it.setBounds(0, 0, sizeInPx, sizeInPx)
        it.setTint(ContextCompat.getColor(context, colorRes))
    }
    textView.setCompoundDrawables(drawable, null, null, null)

    textView.compoundDrawablePadding = 24
    textView.gravity = Gravity.CENTER_VERTICAL

    return this
}

fun Int.toPx(context: android.content.Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

fun Fragment.clearFields(vararg editText: EditText) {
    editText.forEach { it.text?.clear() }
}
