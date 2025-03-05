package com.apk.aboutrasty

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.apk.aboutrasty.databinding.ActivityNoteUpdateBinding

class NoteUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteUpdateBinding
    private lateinit var db: DatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNoteUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = DatabaseHelper(this)

        noteId = intent.getIntExtra("noteId", -1)
        if (noteId == -1){
            finish()
            return
        }

        val note = db.getNoteById(noteId)
        binding.updateEditTextText.setText(note.title)
        binding.updateEditTextText2.setText(note.content)

        binding.btnUpdatenote.setOnClickListener{
            val newTitle = binding.updateEditTextText.text.toString()
            val newContent = binding.updateEditTextText2.text.toString()
            val updatedNote = Note(noteId, newTitle, newContent)
            db.updateNote(updatedNote)
            finish()
            Toast.makeText(this, "Perubahan disimpan!", Toast.LENGTH_SHORT).show()
        }
    }
}
