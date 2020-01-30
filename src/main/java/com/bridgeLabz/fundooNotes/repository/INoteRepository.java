package com.bridgeLabz.fundooNotes.repository;

import java.util.List;

import com.bridgeLabz.fundooNotes.model.Note;

/**
 * Note Repository Interface which has the unimplemented functionality of all
 * note repository related work which has the direct access with the database
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-27
 * @version 1.0
 */
public interface INoteRepository {
	/**
	 * UnImplemented functionality of adding note to the database.
	 * 
	 * @param newNote as Note input parameter
	 * @return Note class
	 */
	public Note saveOrUpdate(Note newNote);

	/**
	 * UnImplemented functionality of finding a note from the database by taking
	 * userId as input parameter.
	 * 
	 * @param noteId as Long input parameter
	 * @return User class
	 */
	public Note getNote(long noteId);

	/**
	 * UnImplemented functionality of deleting the note from database by taking
	 * noteId as input parameter.
	 * 
	 * @param noteId as Long input parameter
	 * @return Boolean
	 */
	public boolean isDeletedNote(long noteId);

	public List<Note> getAllNotes(long userId);

	public List<Note> getAllTrashedNotes(long userId);

	public List<Note> getAllPinnedNotes(long userId);

	public List<Note> getAllArchivedNotes(long userId);

}
