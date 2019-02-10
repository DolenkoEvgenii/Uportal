package etu.uportal.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build

object RateUtil {
    fun rateApp(context: Context) {
        try {
            val rateIntent = rateIntentForUrl(context.packageName, "market://details")
            rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            context.startActivity(rateIntent)
        } catch (e: ActivityNotFoundException) {
            val rateIntent = rateIntentForUrl(context.packageName, "https://play.google.com/store/apps/details")
            context.startActivity(rateIntent)
        }

    }

    private fun rateIntentForUrl(packageName: String, url: String): Intent {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, packageName)))
        var flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        if (Build.VERSION.SDK_INT >= 21) {
            flags = flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
        } else {

            flags = flags or Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
        }
        intent.addFlags(flags)
        return intent
    }
}