package com.androiddigger.notes

import android.app.Application

class NoteApplication : Application() {
    lateinit var noteDatabase: NoteDatabase

    override fun onCreate() {
        noteDatabase = NoteDatabase.getDatabase(this)
        super.onCreate()
    }
}