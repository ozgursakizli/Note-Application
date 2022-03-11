package module

import androidx.room.Database
import androidx.room.RoomDatabase
import module.notes.NoteEntity
import module.notes.NotesDao

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteAppDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

}