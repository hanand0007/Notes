package com.androiddigger.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.androiddigger.notes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainViewModel: MainViewModel by lazy {
        val repository = NoteRepository((application as NoteApplication).noteDatabase.noteDao())
        ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            setLifecycleOwner(this@MainActivity)
            viewModel = mainViewModel
            saveButton.setOnClickListener {
                mainViewModel.saveNoteToDb()
            }
        }

        //observers for live data
        mainViewModel.inputText.observe(this) {
            binding.saveButton.isEnabled = it.trim().isNotEmpty() && it.length > 4 && it.length < 40
        }

        mainViewModel.allNotes.observe(this) {
            val stringBuilder = StringBuilder().apply {
                it.forEach { note ->
                    append("${note.text} \n ${note.date} \n\n")
                }
            }
            mainViewModel.allNotesText.value = stringBuilder.toString()
        }
    }
}