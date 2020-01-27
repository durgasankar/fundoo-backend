package com.bridgeLabz.fundooNotes.service.Implementation;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeLabz.fundooNotes.model.Note;
import com.bridgeLabz.fundooNotes.model.User;
import com.bridgeLabz.fundooNotes.model.dto.NoteDTO;
import com.bridgeLabz.fundooNotes.repository.INoteRepository;
import com.bridgeLabz.fundooNotes.repository.IUserRepository;
import com.bridgeLabz.fundooNotes.service.INoteService;
import com.bridgeLabz.fundooNotes.utility.EMailServiceProvider;
import com.bridgeLabz.fundooNotes.utility.JWTToken;

@Service
public class NoteServiceImpl implements INoteService {

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private INoteRepository noteRepository;
	@Autowired
	private EMailServiceProvider emailServiceProvider;
	@Autowired
	private JWTToken jwtToken;

	@Override
	public boolean createNote(NoteDTO noteDto, String token) {

		long fetchedUserId = jwtToken.decodeToken(token);
		User fetchedUser = userRepository.getUser(fetchedUserId);
		if (fetchedUser != null && fetchedUser.isVerified()) {
			Note newNote = new Note();
			BeanUtils.copyProperties(noteDto, newNote);
			newNote.setCreatedDate(LocalDateTime.now());
			newNote.setColor("white");
			fetchedUser.getNotes().add(newNote);
			noteRepository.save(newNote);
			return true;
		}
		return false;
	}

}
