package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import com.bridgelabz.fundoonotes.model.Note;

public interface IElasticSearchRepository {

	String CreateNote(Note note);

	String UpdateNote(Note note);

	String DeleteNote(Note note);

	List<Note> searchbytitle(String title);

	
}
