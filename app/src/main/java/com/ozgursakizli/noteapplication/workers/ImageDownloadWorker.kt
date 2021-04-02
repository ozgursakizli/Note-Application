package com.ozgursakizli.noteapplication.workers

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ozgursakizli.noteapplication.constants.AppKeyConstants
import com.ozgursakizli.noteapplication.utils.LogUtil
import com.ozgursakizli.noteapplication.utils.Utility
import java.io.File

private val TAG = ImageDownloadWorker::class.java.simpleName

class ImageDownloadWorker(private val context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {
        LogUtil.debug(TAG, "ImageDownloadWorker::download started")
        val imageUrl = inputData.getString(AppKeyConstants.KEY_IMAGE_URL)
        imageUrl?.let { downloadImage(Utility.getRandomString(10), it) }
        return Result.Success()
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
            LogUtil.error(TAG, "downloadImage", e)
        }
    }

}