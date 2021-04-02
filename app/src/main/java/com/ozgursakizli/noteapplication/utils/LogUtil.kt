package com.ozgursakizli.noteapplication.utils

import android.util.Log

object LogUtil {

    fun debug(TAG: String, message: String) {
        if (Utility.isDebugMode()) {
            Log.d(TAG, message)
        }
    }

    fun error(TAG: String, message: String, exception: Throwable? = null) {
        if (Utility.isDebugMode()) {
            Log.e(TAG, message, exception)
        }
    }

}