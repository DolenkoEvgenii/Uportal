package etu.uportal.utils.provider

import android.content.Context
import android.graphics.Color

import cn.pedant.SweetAlert.SweetAlertDialog
import etu.uportal.R

object LoadingDialogProvider {
    fun getProgressDialog(context: Context?): SweetAlertDialog {
        return SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE).apply {
            progressHelper.barColor = Color.parseColor("#A5DC86")
            titleText = context?.getString(R.string.loading) ?: ""
            setCancelable(false)
        }
    }
}
