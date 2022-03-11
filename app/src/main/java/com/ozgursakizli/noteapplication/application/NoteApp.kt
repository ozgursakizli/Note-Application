package com.ozgursakizli.noteapplication.application

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.ozgursakizli.noteapplication.utils.Utility
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class NoteApp : Application(), Configuration.Provider {

    companion object {
        @Volatile
        private lateinit var INSTANCE: NoteApp

        fun getInstance(): NoteApp = INSTANCE
    }

    private var defaultExceptionHandler: Thread.UncaughtExceptionHandler? = null

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    init {
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        val unCaughtExceptionHandler = Thread.UncaughtExceptionHandler { thread: Thread?, ex: Throwable? ->
            Timber.e(ex)
            defaultExceptionHandler?.let {
                thread?.let {
                    ex?.let {
                        defaultExceptionHandler!!.uncaughtException(thread, ex)
                    }
                }
            }
        }
        Thread.setDefaultUncaughtExceptionHandler(unCaughtExceptionHandler)
    }

    override fun onCreate() {
        super.onCreate()
        if (Utility.isDebugMode()) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("------ Beginning of Log File ------")
        INSTANCE = this
    }

    override fun onTerminate() {
        Timber.d("onTerminate")
        super.onTerminate()
    }

    override fun getWorkManagerConfiguration() =
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
}