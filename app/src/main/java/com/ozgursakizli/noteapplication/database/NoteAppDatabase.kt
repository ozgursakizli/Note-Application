package com.ozgursakizli.noteapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ozgursakizli.noteapplication.database.notes.NoteEntity
import com.ozgursakizli.noteapplication.database.notes.NotesDao

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteAppDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

}