package com.ozgursakizli.noteapplication.di

import android.app.Application
import com.ozgursakizli.noteapplication.application.NoteApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideApplication(application: Application): NoteApp = application as NoteApp

}