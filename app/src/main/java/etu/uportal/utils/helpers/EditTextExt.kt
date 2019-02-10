package etu.uportal.utils.helpers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener
import etu.uportal.R

fun EditText.applyPhoneInputMask(valueListener: MaskedTextChangedListener.ValueListener? = null, placeholder: String = context.getString(R.string.phone_mask)) {
    val listener = MaskedTextChangedListener(
            "+7 ([000]) [000] [00] [00]", true, this,
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun afterTextChanged(editable: Editable) {
                    if (editable.isEmpty()) {
                        hint = placeholder
                    }
                }
            },
            valueListener
    )

    addTextChangedListener(listener)
    onFocusChangeListener = listener
    hint = placeholder
}

fun EditText.applyCardInputMask(valueListener: MaskedTextChangedListener.ValueListener? = null,
                                placeholder: String = "[0000] [0000] [0000] [0000]") {
    val listener = MaskedTextChangedListener(
            "[0000] [0000] [0000] [0000]",
            true, this,
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun afterTextChanged(editable: Editable) {
                    if (editable.isEmpty()) {
                        hint = placeholder
                    }
                }
            },
            valueListener
    )
    addTextChangedListener(listener)
    onFocusChangeListener = listener
    hint = placeholder
}