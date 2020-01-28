package com.bridgeLabz.fundooNotes.service.Implementation;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeLabz.fundooNotes.exception.AuthorizationException;
import com.bridgeLabz.fundooNotes.exception.NoteException;
import com.bridgeLabz.fundooNotes.model.Note;
import com.bridgeLabz.fundooNotes.model.User;
import com.bridgeLabz.fundooNotes.model.dto.NoteDTO;
import com.bridgeLabz.fundooNotes.repository.INoteRepository;
import com.bridgeLabz.fundooNotes.repository.IUserRepository;
import com.bridgeLabz.fundooNotes.service.INoteService;
import com.bridgeLabz.fundooNotes.utility.JWTToken;

/**
 * This class implements {@link INoteService} interface which has the
 * unimplemented functionality of creating a note, updating, deleting and all.
 * All operations will be carried out if the user is a valid user.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-27
 * @version 1.0
 */
@Service
public class NoteServiceImpl implements INoteService {

	private static final String NOTE_NOT_FOUND_EXCEPTION_MESSAGE = "Opps...Note not found!";
	private static final String USER_AUTHORIZATION_EXCEPTION_MESSAGE = "Opps...Authorization failed!";
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private INoteRepository noteRepository;
	@Autowired
	private JWTToken jwtToken;

	/**
	 * This function takes {@link NoteDTO} as input parameter and token as path
	 * variable. Using token it authorize the user if the user is verified then all
	 * data of noteDto is copied to the note class and creation dateTime and color
	 * information is saved then the user note information is saved in the database.
	 * After successful saving of note it return boolean value.
	 */
	@Override
	public boolean createNote(NoteDTO noteDto, String token) {
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetchedUser != null) {
			Note newNote = new Note();
			BeanUtils.copyProperties(noteDto, newNote);
			newNote.setCreatedDate(LocalDateTime.now());
			newNote.setColor("white");
			fetchedUser.getNotes().add(newNote);
			noteRepository.saveOrUpdate(newNote);
			return true;
		}
		throw new AuthorizationException(USER_AUTHORIZATION_EXCEPTION_MESSAGE, 401);
	}

	@Override
	public boolean updateNote(NoteDTO noteDto, long noteId, String token) {
		// found authorized user
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetchedUser != null) {
			Note fetchedNote = noteRepository.getNote(noteId);
			// found note
			if (fetchedNote != null) {
				BeanUtils.copyProperties(noteDto, fetchedNote);
				fetchedNote.setUpdatedDate(LocalDateTime.now());
				noteRepository.saveOrUpdate(fetchedNote);
				return true;
			}
			// note not found
			throw new NoteException(NOTE_NOT_FOUND_EXCEPTION_MESSAGE, 300);
		}
		// authentication failed
		throw new AuthorizationException(USER_AUTHORIZATION_EXCEPTION_MESSAGE, 401);
	}

}
