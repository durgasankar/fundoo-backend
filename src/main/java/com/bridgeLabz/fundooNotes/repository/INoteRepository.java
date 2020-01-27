package com.bridgeLabz.fundooNotes.repository;

import org.springframework.stereotype.Component;

import com.bridgeLabz.fundooNotes.model.Note;

@Component
public interface INoteRepository {

	public Note save(Note newNote);

}
