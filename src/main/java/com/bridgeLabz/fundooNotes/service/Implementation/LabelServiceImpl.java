package com.bridgeLabz.fundooNotes.service.Implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
		if (fetchedLabel == null) {
			Label newLabel = new Label();
			BeanUtils.copyProperties(labelDTO, newLabel);
			newLabel.setCreatedDate(LocalDateTime.now());
			fetchedUser.getLabels().add(newLabel);
			labelRepository.save(newLabel);
			return;
		}
		throw new LabelException(Util.LABEL_ALREADY_EXIST_EXCEPTION_MESSAGE, Util.ALREADY_EXIST_EXCEPTION_STATUS);

	}

	@Override
	public boolean createLabelAndMap(String token, long noteId, LabelDTO labelDTO) {
		User fetchedUser = authenticatedUser(token);
		Note fetchedNote = verifiedNote(noteId);
		Label fetchedLabel = labelRepository.findOneBylabelName(labelDTO.getLabelName());
		if (fetchedLabel == null) {
			Label newLabel = new Label();
			BeanUtils.copyProperties(labelDTO, newLabel);
			newLabel.setCreatedDate(LocalDateTime.now());
			fetchedUser.getLabels().add(newLabel);
			fetchedNote.getLabelsList().add(newLabel);
			labelRepository.save(newLabel);
			return true;
		}
		throw new LabelException(Util.LABEL_ALREADY_EXIST_EXCEPTION_MESSAGE, Util.ALREADY_EXIST_EXCEPTION_STATUS);
	}

	@Override
	public boolean addNoteToLabel(String token, long noteId, long labelId) {
		authenticatedUser(token);
		Note fetchedNote = verifiedNote(noteId);
		Optional<Label> fetchedLabel = labelRepository.findById(labelId);
		if (fetchedLabel.isPresent()) {
			fetchedNote.getLabelsList().add(fetchedLabel.get());
			labelRepository.save(fetchedLabel.get());
			return true;
		}
		throw new LabelException(Util.LABEL_ALREADY_EXIST_EXCEPTION_MESSAGE, Util.ALREADY_EXIST_EXCEPTION_STATUS);
	}

	@Override
	public boolean removeNoteFromLabel(String token, long noteId, long labelId) {
		authenticatedUser(token);
		Note fetchedNote = verifiedNote(noteId);
		Optional<Label> fetchedLabel = labelRepository.findById(labelId);
		if (fetchedLabel.isPresent()) {
			fetchedNote.getLabelsList().remove(fetchedLabel.get());
			noteRepository.saveOrUpdate(fetchedNote);
			return true;
		}
		throw new LabelException(Util.LABEL_NOT_FOUND_EXCEPTION_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE);
	}

	@Override
	public boolean isLabelEdited(String token, LabelDTO labelDTO, long labelId) {
		authenticatedUser(token);
		Optional<Label> fetchedLabel = labelRepository.findById(labelId);
		if (fetchedLabel.isPresent()) {
			if (isValidNameForEdit(fetchedLabel, labelDTO)) {
				labelRepository.updateLabelName(labelDTO.getLabelName(), fetchedLabel.get().getLabelId());
				return true;
			}
			return false;
		}
		throw new LabelException(Util.LABEL_NOT_FOUND_EXCEPTION_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE);
	}

	private boolean isValidNameForEdit(Optional<Label> fetchedLabel, LabelDTO labelDTO) {

		if (labelRepository.checkLabelWithDb(labelDTO.getLabelName()).isEmpty()) {
			return !fetchedLabel.get().getLabelName().equals(labelDTO.getLabelName());
		}
		throw new LabelException("name with the given label already exist in your account",
				Util.ALREADY_EXIST_EXCEPTION_STATUS);
	}

	@Override
	public boolean idDeletedLabel(String token, long labelId) {
		authenticatedUser(token);
		Optional<Label> fetchedLabel = labelRepository.findById(labelId);
		if (fetchedLabel.isPresent()) {
			labelRepository.delete(fetchedLabel.get());
			return true;
		}
		throw new LabelException(Util.LABEL_NOT_FOUND_EXCEPTION_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE);
	}

	@Override
	public List<Label> listOfLabels(String token) {
		authenticatedUser(token);
		return labelRepository.getAllLabels();
	}

	@Override
	public List<Note> listOfNotesOfLabel(String token, long labelId) {
		authenticatedUser(token);
		Optional<Label> fetchedLabel = labelRepository.findById(labelId);
		if (fetchedLabel.isPresent()) {
			return fetchedLabel.get().getNoteList();
		}
		throw new LabelException(Util.LABEL_NOT_FOUND_EXCEPTION_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE);
	}

}
