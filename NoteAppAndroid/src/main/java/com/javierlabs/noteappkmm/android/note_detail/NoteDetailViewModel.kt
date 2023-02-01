package com.javierlabs.noteappkmm.android.note_detail

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierlabs.noteappkmm.data.note.SqlDelightNoteDataSource
import com.javierlabs.noteappkmm.domain.note.Note
import com.javierlabs.noteappkmm.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: SqlDelightNoteDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val noteTitle = savedStateHandle.getStateFlow("noteTitle","")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow("isNoteTitleFocused",false)
    private val noteContent = savedStateHandle.getStateFlow("noteContent","")
    private val isNoteContentFocused = savedStateHandle.getStateFlow("isNoteContentFocused",false)
    private val noteColor = savedStateHandle.getStateFlow("noteColor",Note.generateRandomColor())

    val state = combine(
        noteTitle,
        isNoteTitleFocused,
        noteContent,
        isNoteContentFocused,
        noteColor
    ) { title, isTitleFocused, content, isContentFocused, color ->
            NoteDetailState(
                noteTitle = title,
                isNoteTitleHintVisible = title.isEmpty() && isTitleFocused,
                noteContent = content,
                isNoteContentHintVisible = content.isEmpty() && isContentFocused,
                noteColor = color
            )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let {existingNoteId -> //if there is no existing note
            if(existingNoteId == -1L) {
                return@let
            }
            //logic for existing notes
            this.existingNoteId = existingNoteId
            viewModelScope.launch{
                noteDataSource.getNoteById(existingNoteId)?.let { note ->
                    savedStateHandle["noteTitle"] = note.title
                    savedStateHandle["noteContent"] = note.content
                    savedStateHandle["noteColor"] = note.colorHex
                }
            }
        }
    }

    //logic for updating each note
    fun onNoteTitleChanged(text: String){
        savedStateHandle["noteTitle"] = text
    }

    fun onNoteContentChanged(text: String){
        savedStateHandle["noteContent"] = text
    }

    fun onNoteTitleFocusChanged(isFocused: Boolean){
        savedStateHandle["isNoteTitleFocused"] = isFocused
    }

    fun onNoteContentFocusChanged(isFocused: Boolean){
        savedStateHandle["isNoteContentFocused "] = isFocused
    }

    //saving note function
    fun saveNote(){
        viewModelScope.launch{
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }
}