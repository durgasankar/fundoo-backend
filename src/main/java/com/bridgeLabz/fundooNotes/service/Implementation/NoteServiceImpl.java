package com.bridgeLabz.fundooNotes.service.Implementation;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeLabz.fundooNotes.model.DateTimeInfo;
import com.bridgeLabz.fundooNotes.model.Note;
import com.bridgeLabz.fundooNotes.model.User;
import com.bridgeLabz.fundooNotes.model.DTO.NoteDTO;
import com.bridgeLabz.fundooNotes.repository.INoteRepository;
import com.bridgeLabz.fundooNotes.repository.IUserRepository;
import com.bridgeLabz.fundooNotes.service.INoteService;
import com.bridgeLabz.fundooNotes.utility.EMailServiceProvider;
import com.bridgeLabz.fundooNotes.utility.JWTToken;

public class NoteServiceImpl implements INoteService {

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private INoteRepository noteRepository;
	@Autowired
	private EMailServiceProvider emailServiceProvider;
	@Autowired
	private DateTimeInfo dateTimeInfo;
	@Autowired
	private JWTToken jwtToken;

	@Override
	public boolean createNote(NoteDTO noteDto, String token) {

		long fetchedUserId = jwtToken.decodeToken(token);
		User fetchedUser = userRepository.getUser(fetchedUserId);
		if (fetchedUser != null) {
			Note newNote = new Note();
			BeanUtils.copyProperties(noteDto, newNote);
			newNote.getDateTimeInfo().setCreatedInfo(LocalDateTime.now());
			newNote.setDateTimeInfo(dateTimeInfo);
			newNote.setColor("white");
			fetchedUser.getNotes().add(newNote);
			noteRepository.save(newNote);
			return true;
		}
		return false;
	}

}
