package com.example.hokkung.repositorypattern.repository

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.hokkung.repositorypattern.dao.NoteDao
import com.example.hokkung.repositorypattern.entity.Note
import com.example.hokkung.repositorypattern.remote.NoteApis
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NoteRepository(
    private val noteDao: NoteDao,
    private val remote: NoteApis ) {

    private val TAG by lazy { this.javaClass.name }

    @SuppressLint("CheckResult")
    fun insert(note: Note) {
        Observable.fromCallable {
            noteDao.insert(note)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.v(TAG, "insert done!")
            }
    }

    @SuppressLint("CheckResult")
    fun update(note: Note) {
        Observable.fromCallable {
            noteDao.update(note)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.v(TAG, "update done!")
            }
    }

    @SuppressLint("CheckResult")
    fun delete(note: Note) {
        Observable.fromCallable {
            noteDao.delete(note)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.v(TAG, "dalete done!")
            }
    }

    @SuppressLint("CheckResult")
    fun deleteAll() {
        Observable.fromCallable {
            noteDao.deleteAllNotes()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.v(TAG, "dalete all note done!")
            }
    }

    @SuppressLint("CheckResult")
    fun getAllNotes(): LiveData<List<Note>> {
        remote.getAllNotes()
            .subscribeOn(Schedulers.io())
            .subscribe {
                noteDao.insertOrUpdate(*it.toTypedArray())
            }
        return noteDao.getAllNotes()
    }

}