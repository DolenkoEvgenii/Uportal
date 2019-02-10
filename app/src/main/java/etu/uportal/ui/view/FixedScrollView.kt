package etu.uportal.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView

class FixedScrollView: NestedScrollView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS
        isFocusable = true
        isFocusableInTouchMode = true
    }
}