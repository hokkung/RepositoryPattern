package com.example.hokkung.repositorypattern

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.hokkung.repositorypattern.entity.Note
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {

    companion object {
        const val NOTE_DATA = "NOTE_DATA"
        const val EDIT_DATA = "EDIT_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        initView()
        checkIntentData()

    }

    private fun checkIntentData() {
        val intent = intent
        if (intent.hasExtra(NOTE_DATA)) {
            title = "Edit Note"
            val note = intent.getParcelableExtra<Note>(NOTE_DATA)
            setDataEdit(note)
        } else {
            title = "Add Note"
        }
    }

    private fun setDataEdit(note: Note) {
        note.title?.let { edit_text_title.setText(it) }
        note.description?.let { edit_text_desc.setText(it) }
        note.priority?.let { number_picker_priority.value = it }
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set Number Picker
        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 100

    }

    private fun saveNote() {
        val title = edit_text_title.text.toString().trim()
        val desc = edit_text_desc.text.toString().trim()
        val priority = number_picker_priority.value

        checkDataIsEmpty(title, desc, priority)
    }

    private fun sendData(title: String, desc: String, priority: Int) {
        val id = intent.getLongExtra(EDIT_DATA, -1L)
        val note = if (id == -1L) {
            Note(title = title, description = desc, priority = priority)
        } else {
            Note(id= id, title = title, description = desc, priority = priority)
        }
        val intent = Intent()
        intent.putExtra(NOTE_DATA, note)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun checkDataIsEmpty(title: String, desc: String, priority: Int) {
        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(applicationContext, "Please insert title or desc", Toast.LENGTH_SHORT).show()
            return
        }
        sendData(title, desc, priority)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater= menuInflater
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
