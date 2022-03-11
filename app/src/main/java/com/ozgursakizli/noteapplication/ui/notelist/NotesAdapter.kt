package com.ozgursakizli.noteapplication.ui.notelist

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozgursakizli.noteapplication.R
import com.ozgursakizli.noteapplication.databinding.AdapterItemNotesBinding
import com.ozgursakizli.noteapplication.extensions.displayCircularImage
import module.notes.NoteEntity
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter(
    private val onItemClickListener: ItemClickListener,
    private val onItemLongClickListener: ItemLongClickListener,
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var notesList = ArrayList<NoteEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemBinding = AdapterItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val noteEntity: NoteEntity = notesList[position]
        holder.bind(noteEntity)
    }

    override fun getItemCount(): Int = notesList.size

    fun isEmpty(): Boolean {
        return notesList.isNullOrEmpty()
    }

    fun setItems(items: List<NoteEntity>) {
        this.notesList.clear()
        this.notesList.addAll(items)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): NoteEntity? {
        return notesList.getOrNull(position)
    }

    inner class NotesViewHolder(private val itemBinding: AdapterItemNotesBinding) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener, View.OnLongClickListener {

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun bind(noteEntity: NoteEntity) {
            itemBinding.imgNote.displayCircularImage(noteEntity.imageUrl, R.drawable.ic_empty_photo)
            itemBinding.tvTitle.text = noteEntity.title
            itemBinding.tvDescription.text = noteEntity.description
            val dateText: String = SimpleDateFormat(if (DateUtils.isToday(noteEntity.createdDate)) {
                "HH:mm"
            } else {
                "dd.MM.yyyy, HH:mm"
            }, Locale.getDefault()).run { format(noteEntity.createdDate) }
            itemBinding.tvDate.text = String.format("%s", dateText)
        }

        override fun onClick(v: View?) {
            getItem(absoluteAdapterPosition)?.let { onItemClickListener.onItemClick(it) }
        }

        override fun onLongClick(v: View?): Boolean {
            getItem(absoluteAdapterPosition)?.let { onItemLongClickListener.onItemLongClick(it) }
            return true
        }

    }

    interface ItemClickListener {
        fun onItemClick(noteEntity: NoteEntity)
    }

    interface ItemLongClickListener {
        fun onItemLongClick(noteEntity: NoteEntity)
    }

}