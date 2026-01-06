package com.denise.castro.task.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.denise.castro.task.R
import com.denise.castro.task.customview.state.ProgressButtonState
import com.denise.castro.task.databinding.ProgressButtonBinding

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var title: String? = null
    private var loadingTitle: String? = null

    private val binding = ProgressButtonBinding
        .inflate(LayoutInflater.from(context), this, true)

    private var state: ProgressButtonState = ProgressButtonState.Normal
        set(value) {
            field = value
            refreshState()
        }

    init {
        setLayout(attrs)
        refreshState()
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.ProgressButton)

            setBackgroundResource(R.drawable.progress_button_background)

            val titleResId =
                attributes.getResourceId(R.styleable.ProgressButton_progress_button_title, 0)
            if (titleResId != 0) title = context.getString(titleResId)

            val loadingTitleResId =
                attributes.getResourceId(R.styleable.ProgressButton_progress_button_loading_title, 0)
            if (loadingTitleResId != 0) loadingTitle = context.getString(loadingTitleResId)

            attributes.recycle()
        }
    }

    private fun refreshState() {
        isEnabled = state.isEnabled
        binding.textTitle.text =
            if (state is ProgressButtonState.Loading) loadingTitle else title
        binding.progressButton.visibility = state.progressVisibility
    }

    fun setLoading() {
        state = ProgressButtonState.Loading
    }

    fun setNormal() {
        state = ProgressButtonState.Normal
    }
}