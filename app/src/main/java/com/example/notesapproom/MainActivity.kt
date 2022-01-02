package com.example.notesapproom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.DialogInterface
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class MainActivity : AppCompatActivity() {
    lateinit var rvNotes: RecyclerView
    lateinit var edtNote: EditText
    lateinit var lsNote: List<Notes>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NotesDatabase.getInstance(applicationContext)

        edtNote=findViewById(R.id.edtNote)
        rvNotes=findViewById(R.id.rvNotes)
        lsNote= listOf()

        var subBtn=findViewById(R.id.submBtn) as Button
        updtRC()
        subBtn.setOnClickListener {
            val  note=edtNote.text.toString()
            val save = Notes(null,note)
            NotesDatabase.getInstance(applicationContext).NotesDao().insertNote(save)

            updtRC()
            edtNote.text.clear()
            edtNote.clearFocus()

        }
    }

    fun getNotes(){
        lsNote=NotesDatabase.getInstance(applicationContext).NotesDao().getallnotes()
        updtRC()
    }

    fun updtRC(){
        lsNote=NotesDatabase.getInstance(applicationContext).NotesDao().getallnotes()
        rvNotes.adapter = RVAdapter(this,lsNote)
        rvNotes.layoutManager = LinearLayoutManager(this)
    }

    fun updtNote(idNote:Int , Note:String){
        NotesDatabase.getInstance(applicationContext).NotesDao().updtNote(Notes( idNote , Note ))
        Toast.makeText(this, "note updated", Toast.LENGTH_SHORT).show()
        getNotes()
    }

    fun DialogEdit(note : Notes){
        val dialogBuilder = AlertDialog.Builder(this)
        val newNote = EditText(this)
        newNote.hint = "Enter new note"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    dialog, id->

                updtNote(note.id!!,newNote.text.toString())
                updtRC()
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(newNote)
        alert.show()
    }

    fun delNote(note : Notes){
        NotesDatabase.getInstance(applicationContext).NotesDao().delNote(note)
        updtRC()
        Toast.makeText(this, "deleted note successfully!", Toast.LENGTH_SHORT).show()
    }


    fun DialogDel(note : Notes ){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are you sure?")
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id-> delNote(note)

            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Delete Note")
        alert.show()
    }

}