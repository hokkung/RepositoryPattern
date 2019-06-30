package com.example.hokkung.repositorypattern.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hokkung.repositorypattern.entity.Note

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(vararg note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_tb")
    fun deleteAllNotes()

    @Query("SELECT * FROM note_tb ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_tb WHERE id = :id")
    fun getNote(id: Int): LiveData<Note>
}