package etu.uportal.utils.service


import android.content.Context
import android.content.Intent
import android.net.Uri
import java.util.*

object UtilService {
    fun daysBetween(d1: Date, d2: Date): Int {
        return ((d2.time - d1.time) / (1000 * 60 * 60 * 24)).toInt()
    }

    fun openUrl(context: Context?, url: String) {
        var fixedUtl = url
        if (!fixedUtl.startsWith("http://") && !fixedUtl.startsWith("https://")) {
            fixedUtl = "http://$fixedUtl"
        }

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(fixedUtl))
        context?.startActivity(browserIntent)
    }
}
