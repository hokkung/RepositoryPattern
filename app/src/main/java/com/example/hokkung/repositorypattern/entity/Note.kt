package com.example.hokkung.repositorypattern.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "note_tb")
data class Note (

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String?,

    @ColumnInfo(name = "description")
    @SerializedName("body")
    val description: String?,

    @ColumnInfo(name = "priority")
    @SerializedName("userId")
    val priority: Int?

) : Parcelable