package com.example.hokkung.repositorypattern.remote

import com.example.hokkung.repositorypattern.entity.Note
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NoteApis {
    @GET("posts/")
    fun getAllNotes(): Observable<List<Note>>

    companion object {
        fun call(): NoteApis {
            val BASE_URL = "https://jsonplaceholder.typicode.com/"

            val retrofit =  Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(NoteApis::class.java)
        }
    }

}