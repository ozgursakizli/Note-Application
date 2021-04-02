package com.ozgursakizli.noteapplication.database.notes

import kotlinx.coroutines.flow.Flow

interface NotesDataSource {
    fun getAllNotes(): Flow<List<NoteEntity>>
    fun getNoteById(noteId: Long): Flow<NoteEntity>
    suspend fun insert(noteEntity: NoteEntity): Long
    suspend fun delete(noteEntity: NoteEntity)
}