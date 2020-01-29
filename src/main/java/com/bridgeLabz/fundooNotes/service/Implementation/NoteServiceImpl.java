package com.bridgeLabz.fundooNotes.service.Implementation;

import java.time.LocalDateTime;
import java.util.List;

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
	private static final int USER_AUTHENTICATION_EXCEPTION_STATUS = 401;
	private static final int NOTE_NOT_FOUND_EXCEPTION_STATUS = 300;
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
		throw new AuthorizationException(USER_AUTHORIZATION_EXCEPTION_MESSAGE, USER_AUTHENTICATION_EXCEPTION_STATUS);
	}

	/**
	 * This function takes {@link NoteDTO} as input parameter and token as path
	 * variable. Using token it authorize the user if the user is verified then all
	 * data of noteDto is copied to the note class and update time and date is saved
	 * in the database. If user is not valid then {@link AuthorizationException}, if
	 * note is not found in the database then {@link NoteException} is thrown. on
	 * Successful update it display proper message to the user.
	 */
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
			throw new NoteException(NOTE_NOT_FOUND_EXCEPTION_MESSAGE, NOTE_NOT_FOUND_EXCEPTION_STATUS);
		}
		// authentication failed
		throw new AuthorizationException(USER_AUTHORIZATION_EXCEPTION_MESSAGE, USER_AUTHENTICATION_EXCEPTION_STATUS);
	}

	/**
	 * This function takes note id and authorized token from the user checks for
	 * user authorization if valid customer then find for the available note on the
	 * database if it found valid note then it delete permanently from database.If
	 * user is not valid then {@link AuthorizationException}, if note is not found
	 * in the database then {@link NoteException} is thrown. on Successful deletion
	 * of note it display proper message to the user.
	 */
	@Override
	public boolean deleteNote(long noteId, String token) {
		// found authorized user
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetchedUser != null) {
			// found note
			Note fetchedNote = noteRepository.getNote(noteId);
			if (fetchedNote != null) {
				noteRepository.isDeletedNote(noteId);
				return true;
			}
			// note not found
			throw new NoteException(NOTE_NOT_FOUND_EXCEPTION_MESSAGE, NOTE_NOT_FOUND_EXCEPTION_STATUS);
		}
		// authentication failed
		throw new AuthorizationException(USER_AUTHORIZATION_EXCEPTION_MESSAGE, USER_AUTHENTICATION_EXCEPTION_STATUS);
	}

	/**
	 * This function takes note id and authorized token from the user checks for
	 * user authorization if valid customer then find for the available of note on
	 * the database. if found valid note then it change the status of archived on
	 * database. If user is not valid then {@link AuthorizationException}, if note
	 * is not found in the database then {@link NoteException} is thrown. if the
	 * note is archived already then it return false. on Successful change of
	 * archived status of note it return boolean value.
	 */
	@Override
	public boolean archieveNote(long noteId, String token) {
		// found authorized user
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetchedUser != null) {
			// found note
			Note fetchedNote = noteRepository.getNote(noteId);
			if (fetchedNote != null) {
				// fetched note is not archived
				if (!fetchedNote.isArchived()) {
					fetchedNote.setArchived(true);
					fetchedNote.setUpdatedDate(LocalDateTime.now());
					noteRepository.saveOrUpdate(fetchedNote);
					return true;
				}
				// if archived already
				return false;
			}
			// note not found
			throw new NoteException(NOTE_NOT_FOUND_EXCEPTION_MESSAGE, NOTE_NOT_FOUND_EXCEPTION_STATUS);
		}
		// authentication failed
		throw new AuthorizationException(USER_AUTHORIZATION_EXCEPTION_MESSAGE, USER_AUTHENTICATION_EXCEPTION_STATUS);
	}

	/**
	 * This function takes note id and authorized token from the user checks for
	 * user authorization if valid customer then find for the available of note on
	 * the database. if found valid note then it change the status of pinned on
	 * database. If user is not valid then {@link AuthorizationException}, if note
	 * is not found in the database then {@link NoteException} is thrown. if the
	 * note is pinned already then it return false. on Successful change of pinned
	 * status of note it return boolean value.
	 */
	@Override
	public boolean pinNote(long noteId, String token) {
		// found authorized user
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetchedUser != null) {
			// found note
			Note fetchedNote = noteRepository.getNote(noteId);
			if (fetchedNote != null) {
				// fetched note is not pinned
				if (!fetchedNote.isPinned()) {
					fetchedNote.setPinned(true);
					fetchedNote.setUpdatedDate(LocalDateTime.now());
					noteRepository.saveOrUpdate(fetchedNote);
					return true;
				}
				// if pinned already
				return false;
			}
			// note not found
			throw new NoteException(NOTE_NOT_FOUND_EXCEPTION_MESSAGE, NOTE_NOT_FOUND_EXCEPTION_STATUS);
		}
		// authentication failed
		throw new AuthorizationException(USER_AUTHORIZATION_EXCEPTION_MESSAGE, USER_AUTHENTICATION_EXCEPTION_STATUS);
	}

	/**
	 * This function takes note id and authorized token from the user checks for
	 * user authorization if valid customer then find for the available of note on
	 * the database. if found valid note then it change the status of trashed on
	 * database. If user is not valid then {@link AuthorizationException}, if note
	 * is not found in the database then {@link NoteException} is thrown. if the
	 * note is trashed already then it return false. on Successful change of trashed
	 * status of note it return boolean value.
	 */
	@Override
	public boolean trashNote(long noteId, String token) {
		// found authorized user
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetchedUser != null) {
			// found note
			Note fetchedNote = noteRepository.getNote(noteId);
			if (fetchedNote != null) {
				// fetched note is not trashed
				if (!fetchedNote.isTrashed()) {
					fetchedNote.setTrashed(true);
					fetchedNote.setUpdatedDate(LocalDateTime.now());
					noteRepository.saveOrUpdate(fetchedNote);
					return true;
				}
				// if trashed already
				return false;
			}
			// note not found
			throw new NoteException(NOTE_NOT_FOUND_EXCEPTION_MESSAGE, NOTE_NOT_FOUND_EXCEPTION_STATUS);
		}
		// authentication failed
		throw new AuthorizationException(USER_AUTHORIZATION_EXCEPTION_MESSAGE, USER_AUTHENTICATION_EXCEPTION_STATUS);
	}

	@Override
	public List<Note> getallNotes(String token) {
		// found authorized user
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetchedUser != null) {
			// note found
			List<Note> fetchedNotes = noteRepository.getAllNotes(fetchedUser.getUserId());
			if (fetchedNotes != null) {
				return fetchedNotes;
			}
			// note not found
			throw new NoteException(NOTE_NOT_FOUND_EXCEPTION_MESSAGE, NOTE_NOT_FOUND_EXCEPTION_STATUS);
		}
		// authentication failed
		throw new AuthorizationException(USER_AUTHORIZATION_EXCEPTION_MESSAGE, USER_AUTHENTICATION_EXCEPTION_STATUS);
	}

	@Override
	public List<Note> getAllTrashedNotes(String token) {
		// found authorized user
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetchedUser != null) {
			// note found
			List<Note> fetchedTrashedNotes = noteRepository.getAllTrashedNotes(fetchedUser.getUserId());
			if (fetchedTrashedNotes != null) {
				return fetchedTrashedNotes;
			}
			// note not found
			throw new NoteException(NOTE_NOT_FOUND_EXCEPTION_MESSAGE, NOTE_NOT_FOUND_EXCEPTION_STATUS);
		}
		// authentication failed
		throw new AuthorizationException(USER_AUTHORIZATION_EXCEPTION_MESSAGE, USER_AUTHENTICATION_EXCEPTION_STATUS);
	}

	

}
