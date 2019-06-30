package com.example.hokkung.repositorypattern

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hokkung.repositorypattern.adapter.NoteAdapter
import com.example.hokkung.repositorypattern.entity.Note
import com.example.hokkung.repositorypattern.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var vm: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    companion object {
        const val ADD_REQUEST_CODE_NOTE = 1
        const val EDIT_REQUEST_CODE_NOTE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initRecyclerView()
        initRecyclerViewHelper()
        initFloatingButton()
    }

    private fun initRecyclerViewHelper() {
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback
            (0, ItemTouchHelper.LEFT or  ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                vm.delete(noteAdapter.getNote(viewHolder.adapterPosition))
                toast("note delete")
            }
        }).attachToRecyclerView(recyclerView)
    }

    private fun initFloatingButton() {
        floatBtn.setOnClickListener {
            val intent = Intent(applicationContext, NoteActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST_CODE_NOTE)
        }
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        noteAdapter = NoteAdapter(noteClickListener)
        recyclerView.adapter = noteAdapter
    }

    private val noteClickListener = object: NoteAdapter.OnNoteClickListener {
        override fun inItemClick(note: Note) {
            val intent = Intent(applicationContext, NoteActivity::class.java)
            intent.putExtra(NoteActivity.NOTE_DATA, note)
            intent.putExtra(NoteActivity.EDIT_DATA, note.id)
            startActivityForResult(intent, EDIT_REQUEST_CODE_NOTE)
        }
    }

    private fun initViewModel() {
        vm = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        vm.getAllNotes().observe(this, Observer {
            // Update RecyclerView
            noteAdapter.submitList(it)
            toast("change")
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_REQUEST_CODE_NOTE && resultCode == Activity.RESULT_OK) {
            val note = data?.getParcelableExtra<Note>(NoteActivity.NOTE_DATA)
            note?.let { vm.insert(it) }
            toast("note saved")
        } else if (requestCode == EDIT_REQUEST_CODE_NOTE && resultCode == Activity.RESULT_OK) {
            val note = data?.getParcelableExtra<Note>(NoteActivity.NOTE_DATA)
            if (note?.id == -1L) return
            note?.let { vm.update(it) }
            toast("note update")
        } else {
            toast("note not saved")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_note -> {
                vm.deleteAll()
                toast("delete all note")
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }


}
