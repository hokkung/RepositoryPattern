package com.example.hokkung.repositorypattern.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.hokkung.repositorypattern.dao.NoteDao
import com.example.hokkung.repositorypattern.entity.Note
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Database(entities = [Note::class], version = 1)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile var INSTANCE: NoteDataBase? = null

        private val LOCK = Any()

        fun getInstance(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            NoteDataBase::class.java, "note_database.db")
            .fallbackToDestructiveMigration()
            .addCallback(roomCallback)
            .build()

        private val roomCallback = object: RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { asyncDatabase(it) }
            }
        }

        private val asyncDatabase:(NoteDataBase) -> Unit = {
            Observable.fromCallable {
                val noteDao = it.noteDao()
                 noteDao.insert(Note(title = "title1", description = "Des1", priority = 1))
                 noteDao.insert(Note(title = "title2", description = "Des2", priority = 2))
                 noteDao.insert(Note(title = "title3", description = "Des3", priority = 3))
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }


    }

}