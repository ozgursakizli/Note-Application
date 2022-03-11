package com.ozgursakizli.noteapplication.di

import android.content.Context
import androidx.room.Room
import com.ozgursakizli.noteapplication.database.DatabaseConstants
import com.ozgursakizli.noteapplication.database.NoteAppDatabase
import com.ozgursakizli.noteapplication.database.notes.NotesDao
import com.ozgursakizli.noteapplication.database.notes.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): NoteAppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            NoteAppDatabase::class.java,
            DatabaseConstants.NOTE_APP_DATABASE
        ).build()
    }

    @Provides
    fun provideNotesDao(database: NoteAppDatabase): NotesDao {
        return database.notesDao()
    }

    @Singleton
    @Provides
    fun provideNotesRepository(notesDao: NotesDao): NotesRepository {
        return NotesRepository(notesDao)
    }

}