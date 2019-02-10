package etu.uportal.utils.helpers

import android.widget.Toast
import es.dmoral.toasty.Toasty
import etu.uportal.App

@JvmOverloads
fun Any?.showErrorToast(stringResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    showErrorToast(getString(stringResId), duration)
}

@JvmOverloads
fun Any?.showErrorToast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    App.component.context().let {
        Toasty.error(it, message ?: "", duration).show()
    }
}

@JvmOverloads
fun Any?.showInfoToast(stringResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    showInfoToast(getString(stringResId), duration)
}

@JvmOverloads
fun Any?.showInfoToast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    App.component.context().let {
        Toasty.info(it, message ?: "", duration).show()
    }
}


@JvmOverloads
fun Any?.showWarningToast(stringResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    showWarningToast(getString(stringResId), duration)
}

@JvmOverloads
fun Any?.showWarningToast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    App.component.context().let {
        Toasty.warning(it, message ?: "", duration).show()
    }
}


@JvmOverloads
fun Any?.showSuccessToast(stringResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    showSuccessToast(getString(stringResId), duration)
}

@JvmOverloads
fun Any?.showSuccessToast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    App.component.context().let {
        Toasty.success(it, message ?: "", duration).show()
    }
}
