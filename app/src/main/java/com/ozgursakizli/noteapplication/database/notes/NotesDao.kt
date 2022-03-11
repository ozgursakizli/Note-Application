package com.ozgursakizli.noteapplication.database.notes

import androidx.room.*
import com.ozgursakizli.noteapplication.database.DatabaseConstants
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao : NotesDataSource {

    @Query("SELECT * FROM ${DatabaseConstants.NOTES_TABLE} ORDER BY createdDate ASC")
    override fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM ${DatabaseConstants.NOTES_TABLE} WHERE id=:noteId")
    override fun getNoteById(noteId: Int): Flow<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(noteEntity: NoteEntity): Long

    @Delete
    override suspend fun delete(noteEntity: NoteEntity)

}