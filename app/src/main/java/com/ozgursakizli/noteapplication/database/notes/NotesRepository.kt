package com.ozgursakizli.noteapplication.database.notes

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class NotesRepository @Inject constructor(private val notesDao: NotesDao) : NotesDataSource {

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return notesDao.getAllNotes()
    }

    override fun getNoteById(noteId: Long): Flow<NoteEntity> {
        return notesDao.getNoteById(noteId)
    }

    override suspend fun insert(noteEntity: NoteEntity): Long {
        return notesDao.insert(noteEntity)
    }

    override suspend fun delete(noteEntity: NoteEntity) {
        notesDao.delete(noteEntity)
    }

}