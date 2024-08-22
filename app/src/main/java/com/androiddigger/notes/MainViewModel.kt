package com.androiddigger.notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val inputText = MutableLiveData("")
    val allNotesText = MutableLiveData("")

    val allNotes = noteRepository.getAllNotes()

    fun saveNoteToDb() {
        val sdf = SimpleDateFormat("MMM dd,yyyy HH:mm", Locale.getDefault())
        val date = Date(System.currentTimeMillis())
        val note = Note(
            0,
            inputText.value.toString(),
            sdf.format(date)
        )
        inputText.value = ""
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note)
        }
    }

}