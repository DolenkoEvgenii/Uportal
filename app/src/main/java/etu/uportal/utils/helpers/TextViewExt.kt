package etu.uportal.utils.helpers

import android.graphics.Paint
import android.graphics.Typeface
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView

/**
 * Extension method underLine for TextView.
 */
fun TextView.underLine() {
    paint.flags = paint.flags or Paint.UNDERLINE_TEXT_FLAG
    paint.isAntiAlias = true
}

/**
 * Extension method deleteLine for TextView.
 */
fun TextView.deleteLine() {
    paint.flags = paint.flags or Paint.STRIKE_THRU_TEXT_FLAG
    paint.isAntiAlias = true
}

/**
 * Extension method bold for TextView.
 */
fun TextView.bold() {
    paint.isFakeBoldText = true
    paint.isAntiAlias = true
}

/**
 * Extension method to set different color for substring TextView.
 */
fun TextView.setColorOfSubstring(substring: String, color: Int) {
    try {
        val spannable = android.text.SpannableString(text)
        val start = text.indexOf(substring)
        spannable.setSpan(ForegroundColorSpan(color), start, start + substring.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text = spannable
    } catch (e: Exception) {
        Log.d("ViewExtensions", "exception in setColorOfSubstring, text=$text, substring=$substring", e)
    }
}

/**
 * Extension method to set font for TextView.
 */
fun TextView.font(font: String) {
    typeface = Typeface.createFromAsset(context.assets, "fonts/$font.ttf")
}


/**
 * Extension method to set left compound drawable for TextView.
 */
fun TextView.setLeftCompoundDrawable(action: TextView.() -> Unit) {
    setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            val imageWidth = 1.2 * compoundDrawables[0].bounds.width()
            val eventX = event.x.toDouble()

            if (eventX <= imageWidth) {
                this.action()
                return@setOnTouchListener true
            }
        }
        return@setOnTouchListener false
    }
}

/**
 * Extension method to set right compound drawable for TextView.
 */
fun TextView.setRightCompoundDrawable(action: TextView.() -> Unit) {
    setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            val imageWidth = 1.2 * compoundDrawables[2].bounds.width()
            val fieldWidth = right.toDouble()
            val eventX = event.x.toDouble()

            if (eventX >= fieldWidth - imageWidth) {
                this.action()
                return@setOnTouchListener true
            }
        }
        return@setOnTouchListener false
    }
}
