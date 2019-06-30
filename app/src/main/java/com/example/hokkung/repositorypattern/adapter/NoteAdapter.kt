package com.example.hokkung.repositorypattern.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hokkung.repositorypattern.R
import com.example.hokkung.repositorypattern.entity.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(private val noteClickListener: OnNoteClickListener) : ListAdapter<Note, RecyclerView.ViewHolder>(PostDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = getItem(position)
        (holder as? ViewHolder)?.setData(note)
        (holder as? ViewHolder)?.setOnClickListener(noteClickListener, note)
    }

    fun getNote(position: Int): Note = getItem(position)


    class PostDiffCallBack : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setData(note: Note) {
            view.title.text = note.title
            view.priority.text = note.priority.toString()
            view.desc.text = note.description
        }

        fun setOnClickListener(noteClickListener: OnNoteClickListener, note: Note) {
            view.setOnClickListener {
                noteClickListener.inItemClick(note)
            }
        }
    }

    interface OnNoteClickListener {
        fun inItemClick(note: Note)
    }

}