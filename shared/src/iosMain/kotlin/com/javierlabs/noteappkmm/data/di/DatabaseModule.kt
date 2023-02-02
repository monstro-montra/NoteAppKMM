package com.javierlabs.noteappkmm.data.di

import com.javierlabs.noteappkmm.data.local.DatabaseDriverFactory
import com.javierlabs.noteappkmm.data.note.SqlDelightNoteDataSource
import com.javierlabs.noteappkmm.database.NoteDatabase
import com.javierlabs.noteappkmm.domain.note.NoteDataSource

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    private val noteDataSource: NoteDataSource by lazy {
        SqlDelightNoteDataSource(NoteDatabase(factory.createDriver()))
    }
}