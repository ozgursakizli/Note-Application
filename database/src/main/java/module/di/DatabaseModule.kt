package module.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import module.DatabaseConstants
import module.NoteAppDatabase
import module.notes.NotesDao
import module.notes.NotesRepository
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