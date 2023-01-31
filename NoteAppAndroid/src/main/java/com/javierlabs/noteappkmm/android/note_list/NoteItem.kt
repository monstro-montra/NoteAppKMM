package com.javierlabs.noteappkmm.android.note_list

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.javierlabs.noteappkmm.domain.note.Note

@Composable
fun NoteItem(
    note: Note,
    backgroundColor: Color,
    onNoteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
    ) {
    val formattedDate = remember(note.created){

    }
}