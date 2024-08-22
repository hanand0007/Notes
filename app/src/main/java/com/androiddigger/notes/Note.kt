package com.androiddigger.notes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val text: String,
    val date: String
)
