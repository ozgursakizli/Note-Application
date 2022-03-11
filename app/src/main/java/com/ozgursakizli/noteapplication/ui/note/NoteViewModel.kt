package com.ozgursakizli.noteapplication.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozgursakizli.noteapplication.utils.Event
import com.ozgursakizli.noteapplication.utils.EventType
import com.ozgursakizli.noteapplication.utils.NoteEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import module.notes.NoteEntity
import module.notes.NotesRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private var _data = MutableLiveData<NoteEntity>()
    val data: LiveData<NoteEntity> = _data
    private val _event = MutableLiveData<Event<EventType>>()
    val event: LiveData<Event<EventType>> = _event

    fun getNoteById(noteId: Int) {
        Timber.d("getNoteById::noteId: $noteId")
        viewModelScope.launch {
            notesRepository.getNoteById(noteId).collect {
                _data.postValue(it)
            }
        }
    }

    fun insertNote(noteEntity: NoteEntity) {
        Timber.d("insertNote")
        viewModelScope.launch {
            notesRepository.insert(noteEntity)
            _data.postValue(noteEntity)
            _event.postValue(Event(NoteEvents.NoteSaved))
        }
    }

}