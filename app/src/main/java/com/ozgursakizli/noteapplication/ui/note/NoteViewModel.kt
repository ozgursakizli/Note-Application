package com.ozgursakizli.noteapplication.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozgursakizli.noteapplication.database.notes.NoteEntity
import com.ozgursakizli.noteapplication.database.notes.NotesRepository
import com.ozgursakizli.noteapplication.utils.Event
import com.ozgursakizli.noteapplication.utils.EventType
import com.ozgursakizli.noteapplication.utils.LogUtil
import com.ozgursakizli.noteapplication.utils.NoteEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private val TAG = NoteViewModel::class.java.simpleName

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private var _data = MutableLiveData<NoteEntity>()
    val data: LiveData<NoteEntity> = _data
    private val _event = MutableLiveData<Event<EventType>>()
    val event: LiveData<Event<EventType>> = _event

    fun getNoteById(noteId: Long) {
        LogUtil.debug(TAG, "getNoteById::noteId: $noteId")
        viewModelScope.launch {
            notesRepository.getNoteById(noteId).collect {
                _data.postValue(it)
            }
        }
    }

    fun insertNote(noteEntity: NoteEntity) {
        LogUtil.debug(TAG, "insertNote")
        viewModelScope.launch {
            val noteId = notesRepository.insert(noteEntity)
            noteEntity.id = noteId
            _data.postValue(noteEntity)
            _event.postValue(Event(NoteEvents.NoteSaved))
        }
    }

}