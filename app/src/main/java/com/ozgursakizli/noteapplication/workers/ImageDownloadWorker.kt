package com.ozgursakizli.noteapplication.workers

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ozgursakizli.noteapplication.constants.AppKeyConstants
import com.ozgursakizli.noteapplication.utils.Utility
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.io.File

@HiltWorker
class ImageDownloadWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        Timber.d("download started")
        val imageUrl = inputData.getString(AppKeyConstants.KEY_IMAGE_URL)
        imageUrl?.let { downloadImage(Utility.getRandomString(10), it) }
        return Result.success()
    }

    private fun downloadImage(filename: String, downloadUrlOfImage: String) {
        try {
            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            val downloadUri: Uri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator.toString() + filename + ".jpg")
            dm!!.enqueue(request)
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.e("downloadImage", e)
        }
    }

}