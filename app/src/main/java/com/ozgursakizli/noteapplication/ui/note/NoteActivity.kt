package com.ozgursakizli.noteapplication.ui.note

import android.os.Bundle
import android.view.MenuItem
import android.webkit.URLUtil
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.ozgursakizli.noteapplication.R
import com.ozgursakizli.noteapplication.constants.AppKeyConstants
import com.ozgursakizli.noteapplication.database.notes.NoteEntity
import com.ozgursakizli.noteapplication.databinding.ActivityNoteBinding
import com.ozgursakizli.noteapplication.extensions.displayCircularImage
import com.ozgursakizli.noteapplication.utils.EventObserver
import com.ozgursakizli.noteapplication.utils.EventType
import com.ozgursakizli.noteapplication.utils.LogUtil
import com.ozgursakizli.noteapplication.utils.NoteEvents
import com.ozgursakizli.noteapplication.utils.ToastUtil
import com.ozgursakizli.noteapplication.workers.ImageDownloadWorker
import dagger.hilt.android.AndroidEntryPoint

private val TAG = NoteActivity::class.java.simpleName

@AndroidEntryPoint
class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var currentNote: NoteEntity
    private var initialData: NoteEntity? = null
    private var noteId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtil.debug(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadIntent()
        initUi()
        observeViewModel()
    }

    private fun loadIntent() {
        LogUtil.debug(TAG, "loadIntent")
        if (intent != null && intent.extras != null) {
            noteId = intent.extras!!.getLong(AppKeyConstants.KEY_NOTE_ID)
        }
    }

    private fun initUi() {
        LogUtil.debug(TAG, "initUi")
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
        }
        initClickListeners()
    }

    private fun initClickListeners() {
        binding.imgAddNote.setOnClickListener { showAddImageDialog() }
    }

    private fun showAddImageDialog() {
        var inputUrl = ""
        MaterialDialog(this@NoteActivity).show {
            input { _, charSequence -> run { inputUrl = charSequence.toString() } }
            title(R.string.enter_image_url)
            negativeButton(R.string.cancel)
            negativeButton { dismiss() }
            positiveButton(R.string.add_image)
            positiveButton {
                updateNoteWithImage(inputUrl)
            }
            lifecycleOwner(this@NoteActivity)
        }
    }

    private fun updateNoteWithImage(imageUrl: String?) {
        if (URLUtil.isValidUrl(imageUrl)) {
            ToastUtil.shortToast(R.string.valid_image_url)
            currentNote.imageUrl = imageUrl
            saveNote()
            startWorker(imageUrl!!)
        } else {
            ToastUtil.shortToast(R.string.invalid_image_url)
        }
    }

    private fun startWorker(imageUrl: String) {
        val data = Data.Builder()
            .putString(AppKeyConstants.KEY_IMAGE_URL, imageUrl)
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)

        val oneTimeRequest = OneTimeWorkRequest.Builder(ImageDownloadWorker::class.java)
            .setInputData(data)
            .setConstraints(constraints.build())
            .build()

        WorkManager.getInstance(this).enqueueUniqueWork(AppKeyConstants.KEY_DOWNLOAD_IMAGE, ExistingWorkPolicy.KEEP, oneTimeRequest)
    }

    private fun observeViewModel() {
        LogUtil.debug(TAG, "observeViewModel")
        with(noteViewModel) {
            data.observe(this@NoteActivity, {
                if (it != null) {
                    currentNote = it
                    noteId = currentNote.id

                    if (initialData == null) {
                        initialData = currentNote.copy()
                    }

                    binding.apply {
                        imgAddNote.displayCircularImage(currentNote.imageUrl, R.drawable.ic_add_a_photo)
                        edtTitle.setText(currentNote.title)
                        edtDescription.setText(currentNote.description)
                    }
                }
            })
            event.observe(this@NoteActivity, EventObserver(::eventHandler))
        }

        if (noteId == -1L) {
            createNewNote()
        } else {
            noteViewModel.getNoteById(noteId)
        }
    }

    private fun createNewNote() {
        currentNote = NoteEntity(binding.edtTitle.text.toString().trim(), "", "", System.currentTimeMillis())
    }

    private fun saveNote() {
        currentNote.title = binding.edtTitle.text.toString().trim()
        currentNote.description = binding.edtDescription.text.toString().trim()
        noteViewModel.insertNote(currentNote)
    }

    private fun eventHandler(eventType: EventType) {
        when (eventType) {
            is EventType.ShowToast -> ToastUtil.shortToast(eventType.source)
            is EventType.ShowError -> ToastUtil.shortToast(eventType.source)
            is NoteEvents.NoteSaved -> ToastUtil.shortToast(R.string.note_saved_successful)
            else -> Unit
        }
    }

    override fun onBackPressed() {
        saveNote()
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}