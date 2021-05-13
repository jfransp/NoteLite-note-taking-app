package com.example.notelite.ui.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notelite.data.NoteEntity
import com.example.notelite.databinding.ItemNoteBinding

class NotesAdapter(private val listener: OnItemClickListener): ListAdapter<NoteEntity,
        NotesAdapter.NoteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root) {

        //Sets up click listeners - functions are interfaces to me implemented on the Main Screen
        init {
            binding.apply {
                noteButton.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val note = getItem(position)
                        listener.onNoteClick(note)
                    }
                }
                deleteNoteButton.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val note = getItem(position)
                        listener.onDeleteClick(note)
                    }
                }
            }
        }

        //Bind function that adapts the data to the items.
        fun bind(note: NoteEntity) {
            binding.apply {
                titleView.text = note.title
                noteButton.text = note.text
                dateOfCreation.text = note.timeOfCreationFormatted
            }
        }
    }

    //Interface for the click listeners on each item - should be implemented on the Main Screen
    interface OnItemClickListener {
        fun onNoteClick(note: NoteEntity)
        fun onDeleteClick(note: NoteEntity)
    }

    //DiffUtil to compare and update items
    class DiffCallback: DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity) =
            oldItem == newItem
    }
}