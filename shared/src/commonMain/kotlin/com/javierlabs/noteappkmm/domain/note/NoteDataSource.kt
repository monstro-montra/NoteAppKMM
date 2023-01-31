package com.javierlabs.noteappkmm.domain.note

interface NoteDataSource {
    suspend fun insertNote(note: Note)
    suspend fun getNoteById(id: Long): Note? //nullable note
    suspend fun getAllNotes(): List<Note>
    suspend fun deleteNoteById(id: Long)
}