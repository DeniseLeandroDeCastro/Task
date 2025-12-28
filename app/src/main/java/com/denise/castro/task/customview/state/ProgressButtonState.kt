package com.denise.castro.task.customview.state

import android.view.View

sealed class ProgressButtonState(
    val isEnabled: Boolean,
    val progressVisibility: Int
) {
    object Normal: ProgressButtonState(true, View.GONE)
    object Loading: ProgressButtonState(false, View.VISIBLE)
}