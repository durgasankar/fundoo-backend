package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import com.bridgelabz.fundoonotes.model.Note;

/**
 * Repository of elastic search which provides unimplemented functionality of
 * create , update, delete, search operation of {@link Note}.
 * 
 * @author Durgasankar Mishra
 * @created 2020-03-04
 * @version 1.0
 */
public interface IElasticSearchRepository {
	/**
	 * UnImplemented functionality of adding note to the elastic search database.
	 * 
	 * @param note as {@link Note}
	 */
	public void createNote(Note note);

	/**
	 * UnImplemented functionality of updating note to the database.
	 * 
	 * @param note as {@link Note}
	 */
	public String updateNote(Note note);

	/**
	 * UnImplemented functionality of deleting note from the database.
	 * 
	 * @param note as {@link Note}
	 */
	public String deleteNote(Note note);

	/**
	 * UnImplemented functionality of searching a note from the database.
	 * 
	 * @param title as String
	 * @return List<Note>
	 */
	public List<Note> searchByTitle(String title);

}
