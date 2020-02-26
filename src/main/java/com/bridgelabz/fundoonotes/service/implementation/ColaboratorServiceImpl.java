package com.bridgelabz.fundoonotes.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.exception.AuthorizationException;
import com.bridgelabz.fundoonotes.exception.ColaboratorException;
import com.bridgelabz.fundoonotes.exception.NoteException;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.INoteRepository;
import com.bridgelabz.fundoonotes.repository.IUserRepository;
import com.bridgelabz.fundoonotes.service.IColaboratorService;
import com.bridgelabz.fundoonotes.utility.JWTToken;
import com.bridgelabz.fundoonotes.utility.Util;

/**
 * 
 * @author Durgasankar Mishra
 * @created 2020-02-09
 * @version 1.0
 */
@Service
public class ColaboratorServiceImpl implements IColaboratorService {
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private INoteRepository noteRepository;
	@Autowired
	private JWTToken jwtToken;

	/**
	 * This function takes authentication token as String input parameter and decode
	 * token an authenticate user after successful authentication it return the
	 * verified user else throw {@link AuthorizationException}
	 * 
	 * @param token as String input parameter
	 * @return {@link User}
	 */
	private User authenticatedMainUser(String token) {
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetchedUser != null) {
			return fetchedUser;
		}
		throw new AuthorizationException(Util.USER_AUTHORIZATION_EXCEPTION_MESSAGE,
				Util.USER_AUTHENTICATION_EXCEPTION_STATUS);
	}

	/**
	 * This function takes note id as input parameter check for it's existence in
	 * the database if found valid then return it else throw {@link NoteException}
	 * 
	 * @param noteId as Long input parameter
	 * @return {@link Note}
	 */
	private Note verifiedNote(long noteId) {
		Note fetchedNote = noteRepository.getNote(noteId);
		if (fetchedNote != null) {
			if (!fetchedNote.isTrashed()) {
				return fetchedNote;
			}
			throw new NoteException("Opps...Note is trashed!", Util.NOTE_NOT_FOUND_EXCEPTION_STATUS);
		}
		throw new NoteException(Util.NOTE_NOT_FOUND_EXCEPTION_MESSAGE, Util.NOTE_NOT_FOUND_EXCEPTION_STATUS);
	}

	private User validColaborator(String emailId) {
		User fetchedColaborator = userRepository.getUser(emailId);
		if (fetchedColaborator != null && fetchedColaborator.isVerified()) {
			return fetchedColaborator;
		}
		throw new ColaboratorException("Opps...Colaborator is not valid user!", Util.NOT_FOUND_RESPONSE_CODE);
	}

	@Override
	public boolean addColaborator(String token, long noteId, String emailId) {
		if (authenticatedMainUser(token).getEmailId().equals(emailId)) {
			throw new ColaboratorException("Opps...Can't add own account as colaborator", 400);
		}
		Note fetchedValidNote = verifiedNote(noteId);
		User fetchedValidColaborator = validColaborator(emailId);
		fetchedValidNote.getColaboratedUsers().add(fetchedValidColaborator);
		fetchedValidColaborator.getColaboratedNotes().add(fetchedValidNote);
		userRepository.save(fetchedValidColaborator);
		return true;
	}

	@Override
	public List<User> getColaboratorsOfNote(String token, long noteId) {
		authenticatedMainUser(token);
		return verifiedNote(noteId).getColaboratedUsers();
	}

	@Override
	public boolean removeColaborator(String token, long noteId, String emailId) {
		authenticatedMainUser(token);
		Note fetchedValidNote = verifiedNote(noteId);
		User fetchedValidColaborator = validColaborator(emailId);
		fetchedValidNote.getColaboratedUsers().remove(fetchedValidColaborator);
		fetchedValidColaborator.getColaboratedNotes().remove(fetchedValidNote);
		userRepository.save(fetchedValidColaborator);
		return true;
	}

}
