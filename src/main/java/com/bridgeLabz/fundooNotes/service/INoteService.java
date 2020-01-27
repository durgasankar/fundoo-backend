package com.bridgeLabz.fundooNotes.service;

import org.springframework.stereotype.Component;

import com.bridgeLabz.fundooNotes.model.dto.NoteDTO;

@Component
public interface INoteService {
	
	public boolean createNote(NoteDTO noteDto, String token);

}
