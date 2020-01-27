package com.bridgeLabz.fundooNotes.service;

import com.bridgeLabz.fundooNotes.model.DTO.NoteDTO;

public interface INoteService {
	
	public boolean createNote(NoteDTO noteDto, String token);

}
