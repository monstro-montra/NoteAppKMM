package com.javierlabs.noteappkmm.domain.note

import com.javierlabs.noteappkmm.time.DateTimeUtil

class SearchNotes {
    fun execute(notes: List<Note>, query: String): List<Note>{
        if(query.isBlank()){
            return notes
        }
        return notes.filter{
            it.title.trim().lowercase().contains(query.lowercase()) || //converts everything to lowercase
                it.content.trim().lowercase().contains(query.lowercase())
        } .sortedBy {
            DateTimeUtil.toEpochMillis(it.created)
        }
    }

}