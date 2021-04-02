package com.ozgursakizli.noteapplication.database.notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ozgursakizli.noteapplication.database.DatabaseConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM ${DatabaseConstants.NOTES_TABLE} ORDER BY createdDate ASC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM ${DatabaseConstants.NOTES_TABLE} WHERE id=:noteId")
    fun getNoteById(noteId: Long): Flow<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteEntity: NoteEntity): Long

    @Delete
    suspend fun delete(noteEntity: NoteEntity)

}