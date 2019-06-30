package com.example.hokkung.repositorypattern.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hokkung.repositorypattern.dao.NoteDao
import com.example.hokkung.repositorypattern.database.NoteDataBase
import com.example.hokkung.repositorypattern.entity.Note
import com.example.hokkung.repositorypattern.remote.NoteApis
import com.example.hokkung.repositorypattern.repository.NoteRepository

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var database: NoteDataBase = NoteDataBase.getInstance(application)
    private var noteDao: NoteDao
    private val repository: NoteRepository
    private val noteApis by lazy { NoteApis.call() }
    private val notes: LiveData<List<Note>>

    private val TAG by lazy { "NoteViewModel" }

    init {
        noteDao = database.noteDao()
        repository = NoteRepository(noteDao, noteApis)
        notes = repository.getAllNotes()
    }


    fun insert(note: Note) {
        Log.v(TAG, note.toString())
        repository.insert(note)
    }


    fun update(note: Note) {
        repository.update(note)
    }


    fun delete(note: Note) {
        repository.delete(note)
    }


    fun deleteAll() {
        repository.deleteAll()
    }

    fun getAllNotes(): LiveData<List<Note>> = notes

}