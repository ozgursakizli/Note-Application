package com.ozgursakizli.noteapplication.utils.glidemodule

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.ozgursakizli.noteapplication.utils.Utility

@GlideModule
class NoteAppGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        if (Utility.isDebugMode()) {
            builder.setLogLevel(Log.VERBOSE)
        }
        super.applyOptions(context, builder)
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

}