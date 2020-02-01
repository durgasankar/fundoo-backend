package com.bridgeLabz.fundooNotes.service.Implementation;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeLabz.fundooNotes.exception.AuthorizationException;
import com.bridgeLabz.fundooNotes.exception.LabelException;
import com.bridgeLabz.fundooNotes.exception.NoteException;
import com.bridgeLabz.fundooNotes.model.Label;
import com.bridgeLabz.fundooNotes.model.Note;
import com.bridgeLabz.fundooNotes.model.User;
import com.bridgeLabz.fundooNotes.model.dto.LabelDTO;
import com.bridgeLabz.fundooNotes.repository.ILabelRepository;
import com.bridgeLabz.fundooNotes.repository.INoteRepository;
import com.bridgeLabz.fundooNotes.repository.IUserRepository;
import com.bridgeLabz.fundooNotes.service.ILabelService;
import com.bridgeLabz.fundooNotes.utility.JWTToken;
import com.bridgeLabz.fundooNotes.utility.Util;

@Service
public class LabelServiceImpl implements ILabelService {

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private ILabelRepository labelRepository;
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
	private User authenticatedUser(String token) {
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
			return fetchedNote;
		}
		throw new NoteException(Util.NOTE_NOT_FOUND_EXCEPTION_MESSAGE, Util.NOTE_NOT_FOUND_EXCEPTION_STATUS);
	}

	@Override
	public void createLabel(String token, LabelDTO labelDTO) {
		User fetchedUser = authenticatedUser(token);
		Label fetchedLabel = labelRepository.findOneBylabelName(labelDTO.getLabelName());
		if (fetchedLabel != null) {
			throw new LabelException("Opps...Label already exist!", 208);
		}
		Label newLabel = new Label();
		BeanUtils.copyProperties(labelDTO, newLabel);
		newLabel.setCreatedDate(LocalDateTime.now());
		fetchedUser.getLabels().add(newLabel);
		labelRepository.save(newLabel);
	}

	@Override
	public boolean createLabelAndMap(String token, long noteId, LabelDTO labelDTO) {
		User fetchedUser = authenticatedUser(token);
		Note fetchedNote = verifiedNote(noteId);
		Label fetchedLabel = labelRepository.findOneBylabelName(labelDTO.getLabelName());		
		if (fetchedLabel != null) {
			fetchedUser.getLabels().add(fetchedLabel);
			fetchedNote.getLabelsList().add(fetchedLabel);
			userRepository.save(fetchedUser);
			noteRepository.saveOrUpdate(fetchedNote);
			return true;
		}
		
		throw new LabelException("Opps...Label already exist!", 208);
	}

}
