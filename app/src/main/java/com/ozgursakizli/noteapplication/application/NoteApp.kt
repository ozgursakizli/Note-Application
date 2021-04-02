package com.ozgursakizli.noteapplication.application

import android.app.Application
import com.ozgursakizli.noteapplication.utils.LogUtil
import dagger.hilt.android.HiltAndroidApp

private val TAG = NoteApp::class.java.simpleName

@HiltAndroidApp
class NoteApp : Application() {

    companion object {
        @Volatile
        private lateinit var INSTANCE: NoteApp

        fun getInstance(): NoteApp = INSTANCE
    }

    private var defaultExceptionHandler: Thread.UncaughtExceptionHandler? = null

    init {
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        val unCaughtExceptionHandler = Thread.UncaughtExceptionHandler { thread: Thread?, ex: Throwable? ->
            LogUtil.error(TAG, "NoteApp", ex)
            defaultExceptionHandler?.let { thread?.let { ex?.let { defaultExceptionHandler!!.uncaughtException(thread, ex) } } }
        }
        Thread.setDefaultUncaughtExceptionHandler(unCaughtExceptionHandler)
    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.debug(TAG, "------ Beginning of Log File ------")
        INSTANCE = this
    }

    override fun onTerminate() {
        LogUtil.debug(TAG, "onTerminate")
        super.onTerminate()
    }

}