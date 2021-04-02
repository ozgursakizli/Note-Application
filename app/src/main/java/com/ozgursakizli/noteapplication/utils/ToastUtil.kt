package com.ozgursakizli.noteapplication.utils

import android.widget.Toast
import com.ozgursakizli.noteapplication.application.NoteApp

object ToastUtil {

    private fun getContext() = NoteApp.getInstance().applicationContext

    fun shortToast(resId: Int) {
        Toast.makeText(getContext(), getContext().getString(resId), Toast.LENGTH_SHORT).show()
    }

    fun shortToast(text: String?) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show()
    }

    fun longToast(resId: Int) {
        Toast.makeText(getContext(), getContext().getString(resId), Toast.LENGTH_LONG).show()
    }

    fun longToast(text: String?) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show()
    }


}