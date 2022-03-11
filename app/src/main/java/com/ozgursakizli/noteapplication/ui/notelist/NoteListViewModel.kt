package com.ozgursakizli.noteapplication.ui.notelist

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
class NoteListViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private var _data = MutableLiveData<List<NoteEntity>>()
    val data: LiveData<List<NoteEntity>> = _data
    private val _event = MutableLiveData<Event<EventType>>()
    val event: LiveData<Event<EventType>> = _event

    fun getAllNotes() {
        Timber.d("getAllNotes")
        viewModelScope.launch {
            notesRepository.getAllNotes().collect {
                _data.postValue(it)
            }
        }
    }

    fun deleteNote(noteEntity: NoteEntity) {
        Timber.d("deleteNote")
        viewModelScope.launch {
            notesRepository.delete(noteEntity)
            _event.postValue(Event(NoteEvents.NoteDeleted))
        }
    }

}