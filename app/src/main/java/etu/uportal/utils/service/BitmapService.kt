package etu.uportal.utils.service

import android.app.DownloadManager
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment

import java.io.File

object BitmapService {
    fun calculateInSampleSize(options: BitmapFactory.Options, maxSize: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > maxSize || width > maxSize)
            if (height >= width)
                inSampleSize = height / maxSize
            else
                inSampleSize = width / maxSize

        return inSampleSize
    }

    fun downloadImageToGallery(context: Context, imageUrl: String) {
        if (imageUrl.contains(".")) {
            val filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1)

            val direct = File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .absolutePath + "/Haglar/")


            if (!direct.exists()) {
                direct.mkdir()
            }

            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri = Uri.parse(imageUrl)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                            File.separator + "Haglar" + File.separator + filename)

            dm.enqueue(request)
        }
    }
}
