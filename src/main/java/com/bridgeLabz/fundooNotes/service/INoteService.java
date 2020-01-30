package com.bridgeLabz.fundooNotes.service;

import java.util.List;

import com.bridgeLabz.fundooNotes.model.Note;
import com.bridgeLabz.fundooNotes.model.dto.NoteDTO;
import com.bridgeLabz.fundooNotes.model.dto.RemainderDTO;

/**
 * This interface has the UnImplemented functionality of registering note,
 * updating status of note, archive, trash, pinning functionality of the user's
 * note after verifying with the identity.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-22
 * @version 1.0
 */
public interface INoteService {
	/**
	 * UnImplemented functionality of creating a personalized note of a customer
	 * after validating the authorization token.
	 * 
	 * @param noteDto as {@link NoteDTO} input parameter
	 * @param token   as String input parameter
	 * @return Boolean
	 */
	public boolean createNote(NoteDTO noteDto, String token);

	/**
	 * UnImplemented functionality of updating a personalized note of a customer
	 * after validating the authorization token and note id.
	 * 
	 * @param noteDto as {@link NoteDTO} input parameter
	 * @param noteId  as long input parameter
	 * @param token   as String input parameter
	 * @return Boolean
	 */
	public boolean updateNote(NoteDTO noteDto, long noteId, String token);

	/**
	 * UnImplemented functionality of deleting a personalized note of a customer
	 * after validating the authorization token based on note id .
	 * 
	 * @param noteId as long input parameter
	 * @param token  as String input parameter
	 * @return Boolean
	 */
	public boolean deleteNote(long noteId, String token);

	/**
	 * UnImplemented functionality of archiving a personalized note of a customer
	 * after validating the authorization token based on note id .
	 * 
	 * @param noteId as long input parameter
	 * @param token  as String input parameter
	 * @return Boolean
	 */
	public boolean archieveNote(long noteId, String token);

	/**
	 * UnImplemented functionality of pinning a personalized note of a customer
	 * after validating the authorization token based on note id .
	 * 
	 * @param noteId as long input parameter
	 * @param token  as String input parameter
	 * @return Boolean
	 */
	public boolean pinNote(long noteId, String token);

	/**
	 * UnImplemented functionality of adding a personalized note of a customer to
	 * trash after validating the authorization token based on note id .
	 * 
	 * @param noteId as long input parameter
	 * @param token  as String input parameter
	 * @return Boolean
	 */
	public boolean trashNote(long noteId, String token);

	/**
	 * UnImplemented functionality of getting all personalized notes of the user
	 * after validating the authorization token based on note id.
	 * 
	 * @param token as String input parameter
	 * @return List<Note>
	 */
	public List<Note> getallNotes(String token);

	/**
	 * UnImplemented functionality of getting all personalized trashed notes of the
	 * user after validating the authorization token based on note id.
	 * 
	 * @param token as String input parameter
	 * @return List<Note>
	 */
	public List<Note> getAllTrashedNotes(String token);

	/**
	 * UnImplemented functionality of getting all personalized pinned notes of the
	 * user after validating the authorization token based on note id.
	 * 
	 * @param token as String input parameter
	 * @return List<Note>
	 */
	public List<Note> getAllPinnedNotes(String token);

	/**
	 * UnImplemented functionality of getting all personalized archived notes of the
	 * user after validating the authorization token based on note id.
	 * 
	 * @param token as String input parameter
	 * @return List<Note>
	 */
	public List<Note> getAllArchivedNotes(String token);

	public void changeColour(String token, long noteId, String noteColor);

	public void setRemainderforNote(String token, long noteId, RemainderDTO remainderDTO);

}
