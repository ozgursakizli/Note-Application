package com.ozgursakizli.noteapplication.ui.notelist

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

private val TAG = NoteListViewModel::class.java.simpleName

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private var _data = MutableLiveData<List<NoteEntity>>()
    val data: LiveData<List<NoteEntity>> = _data
    private val _event = MutableLiveData<Event<EventType>>()
    val event: LiveData<Event<EventType>> = _event

    fun getAllNotes() {
        LogUtil.debug(TAG, "getAllNotes")
        viewModelScope.launch {
            notesRepository.getAllNotes().collect {
                _data.postValue(it)
            }
        }
    }

    fun deleteNote(noteEntity: NoteEntity) {
        LogUtil.debug(TAG, "deleteNote")
        viewModelScope.launch {
            notesRepository.delete(noteEntity)
            _event.postValue(Event(NoteEvents.NoteDeleted))
        }
    }

}