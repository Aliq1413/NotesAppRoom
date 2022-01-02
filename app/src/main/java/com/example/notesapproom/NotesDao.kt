package com.example.notesapproom

import androidx.room.*
@Dao
interface NotesDao {
    @Query("SELECT * FROM Notes ")
    fun getallnotes(): List<Notes>

    @Insert
    fun insertNote(note: Notes)

    @Update
    fun updtNote(note: Notes)

    @Delete
    fun delNote(note:Notes)
}