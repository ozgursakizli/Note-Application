package com.ozgursakizli.noteapplication.utils

import com.ozgursakizli.noteapplication.BuildConfig

object Utility {

    fun isDebugMode(): Boolean = BuildConfig.DEBUG

    fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}