//
//  NoteItem.swift
//  NoteAppIOS
//
//  Created by Alex Jimenez on 2/1/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteItem: View {
    var note: Note
    var onDeleteClick: () -> Void
    
    var body: some View{
        VStack(alignment: .leading) { //vertical column for note
            HStack{ //horizontal row
                Text(note.title) //create title for note
                    .font(.title3)
                    .fontWeight(.semibold)
                Spacer()
                Button(action: onDeleteClick){ //create delete button
                    Image(systemName: "xmark").foregroundColor(.black)
                }
            }.padding(.bottom, 3) //how much spacing between the vstack and whats below it
            
            Text(note.content)
                .fontWeight(.light)
                .padding(.bottom, 3)
            
            HStack{
                Spacer()// space the time text to go all the way to the right
                Text(DateTimeUtil().formatNoteDate(dateTime: note.created))
                    .font(.footnote)
                    .fontWeight(.light)
            }
        }
        .padding() //default padding
        .background(Color(hex: note.colorHex))
    }
}


struct NoteItem_Previews: PreviewProvider {
    static var previews: some View {
        NoteItem(
            note: Note(id: nil, title: "My Note", content: "Note Content",
               colorHex: 0xFF2341, created: DateTimeUtil().now()),
            onDeleteClick: {}
        )
    }
}
