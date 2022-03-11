package com.ozgursakizli.noteapplication.ui.notelist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.ozgursakizli.noteapplication.R
import com.ozgursakizli.noteapplication.constants.AppKeyConstants
import com.ozgursakizli.noteapplication.databinding.ActivityNoteListBinding
import com.ozgursakizli.noteapplication.extensions.setVisibility
import com.ozgursakizli.noteapplication.ui.note.NoteActivity
import com.ozgursakizli.noteapplication.utils.EventObserver
import com.ozgursakizli.noteapplication.utils.EventType
import com.ozgursakizli.noteapplication.utils.NoteEvents
import com.ozgursakizli.noteapplication.utils.ToastUtil
import dagger.hilt.android.AndroidEntryPoint
import module.notes.NoteEntity
import timber.log.Timber

@AndroidEntryPoint
class NoteListActivity : AppCompatActivity(), NotesAdapter.ItemClickListener, NotesAdapter.ItemLongClickListener {

    private lateinit var binding: ActivityNoteListBinding
    private val notesAdapter by lazy { NotesAdapter(this, this) }
    private val notesViewModel: NoteListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        observeViewModel()
    }

    private fun initUi() {
        Timber.d("initUi")
        binding.rvNotes.apply {
            addItemDecoration(DividerItemDecoration(this@NoteListActivity, DividerItemDecoration.VERTICAL))
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        Timber.d("observeViewModel")
        with(notesViewModel) {
            data.observe(this@NoteListActivity) {
                notesAdapter.setItems(it)
                binding.apply {
                    rvNotes.setVisibility(notesAdapter.isEmpty().not())
                    emptyView.setVisibility(notesAdapter.isEmpty())
                }
            }
            event.observe(this@NoteListActivity, EventObserver(::eventHandler))
            getAllNotes()
        }
    }

    private fun eventHandler(eventType: EventType) {
        when (eventType) {
            is NoteEvents.NoteDeleted -> {
                ToastUtil.shortToast(R.string.note_deleted_successful)
                notesAdapter.notifyDataSetChanged()
            }
            else -> Unit
        }
    }

    private fun openNoteActivity(noteEntity: NoteEntity?) {
        Intent(this, NoteActivity::class.java).apply {
            noteEntity?.let { putExtra(AppKeyConstants.KEY_NOTE_ID, it.id) }
            startActivity(this)
        }
    }

    override fun onItemClick(noteEntity: NoteEntity) {
        openNoteActivity(noteEntity)
    }

    override fun onItemLongClick(noteEntity: NoteEntity) {
        MaterialDialog(this).show {
            message(R.string.delete_note_confirm)
            negativeButton(R.string.cancel)
            negativeButton { dismiss() }
            positiveButton(R.string.delete)
            positiveButton { notesViewModel.deleteNote(noteEntity) }
            lifecycleOwner(this@NoteListActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_note_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_new_note -> {
                openNoteActivity(null)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}