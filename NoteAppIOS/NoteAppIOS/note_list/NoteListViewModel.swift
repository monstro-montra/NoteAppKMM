//
//  NoteListViewModel.swift
//  NoteAppIOS
//
//  Created by Alex Jimenez on 2/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

extension NoteListScreen {
    @MainActor class NoteListViewModel: ObservableObject {
        private var notes = [Note]()
        private var noteDataSource: NoteDataSource? = nil
        private let searchNotes = SearchNotes()
        
        @Published private(set) var filteredNotes = [Note]()
        @Published var searchText = "" {
            didSet{
                self.filteredNotes = searchNotes.execute(notes: self.notes, query: searchText)
            }
        }
        @Published  private (set) var isSearchActive = false
        
        init(noteDataSource: NoteDataSource? = nil) {
            self.noteDataSource = noteDataSource
        }
        
        func deleteNoteById (id: Int64?){ //? means nullable
            if id != nil{
                noteDataSource?.deleteNoteById(id: id!, completionHandler: { error in
                    self.loadNotes() })
            }
        }
        
        func loadNotes() {
            noteDataSource?.getAllNotes(completionHandler: { notes, error in
                self.notes = notes ?? []
                self.filteredNotes = self.notes
            })
        }
        
        func toggleIsSearchActive(){
            isSearchActive = !isSearchActive //set to inactive and if inactive, set searchText to empty string
            if !isSearchActive{
                searchText = ""
            }
        }

        
        func setNoteDataSource(noteDataSource: NoteDataSource){
            self.noteDataSource = noteDataSource
        }
    }
}
