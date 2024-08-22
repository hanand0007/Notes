package com.androiddigger.notes

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {

    fun getAllNotes(): LiveData<List<Note>> {
        return noteDao.getNotes()
    }

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

}