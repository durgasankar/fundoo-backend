package com.bridgeLabz.fundooNotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeLabz.fundooNotes.model.Note;
import com.bridgeLabz.fundooNotes.model.dto.NoteDTO;
import com.bridgeLabz.fundooNotes.model.dto.RemainderDTO;
import com.bridgeLabz.fundooNotes.model.dto.UserDTO;
import com.bridgeLabz.fundooNotes.response.Response;
import com.bridgeLabz.fundooNotes.service.INoteService;
import com.bridgeLabz.fundooNotes.service.Implementation.NoteServiceImpl;
import com.bridgeLabz.fundooNotes.utility.Util;

/**
 * By using the object reference of service class This class has the
 * functionality of getting connected with the user on the user specific request
 * it will redirect to the respective controller method and carry out
 * functionality of that particular request.and respectively note operation is
 * carried out for valid user.
 * 
 * @author Durgasankar Mishra
 * @created 2020-01-27
 * @version 1.0
 * @see {@link INoteService} implementation of all note functionality
 * @see {@link Response} if there is any type of response it will reflect out
 */
@RestController
@RequestMapping("note")
public class NoteController {

	@Autowired
	private INoteService noteService;

	/**
	 * This function takes {@link NoteDTO} as request body and token from
	 * {@link RequestHeader} and verify originality of client
	 * {@link NoteServiceImpl} and accordingly returns the response.
	 * 
	 * @param noteDto as {@link UserDTO}
	 * @param token   as String input parameter
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/note/create
	 */
	@PostMapping("create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO noteDto, @RequestHeader("token") String token) {
		if (noteService.createNote(noteDto, token)) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("note created", Util.CREATED_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps... Error creating note", Util.BAD_REQUEST_RESPONSE_CODE));
	}

	/**
	 * This function takes {@link NoteDTO} as request body and token from
	 * {@link RequestHeader} and verify originality of client
	 * {@link NoteServiceImpl} and after update accordingly it returns the response.
	 * 
	 * @param noteDto as {@link UserDTO}
	 * @param token   as String input parameter
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/note/update?id=85
	 */
	@PutMapping("update")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDTO noteDto, @RequestParam("id") long noteId,
			@RequestHeader("token") String token) {
		if (noteService.updateNote(noteDto, noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note updated", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Error updating note", Util.BAD_REQUEST_RESPONSE_CODE));
	}

	/**
	 * This function takes noteId as {@link PathVariable} and token as
	 * {@link RequestHeader} and verify originality of client
	 * {@link NoteServiceImpl} and after deletion operation of note accordingly it
	 * returns the response.
	 * 
	 * @param noteId as {@link PathVariable}
	 * @param token  as {@link RequestHeader}
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/note/76/delete
	 */
	@DeleteMapping("{id}/delete")
	public ResponseEntity<Response> deleteNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (noteService.deleteNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note deleted", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Error deleting note", Util.BAD_REQUEST_RESPONSE_CODE));

	}

	/**
	 * This function takes noteId as {@link PathVariable} and token as
	 * {@link RequestHeader} and verify originality of client
	 * {@link NoteServiceImpl} and after archive operation of note accordingly it
	 * returns the response.
	 * 
	 * @param noteId as {@link PathVariable}
	 * @param token  as {@link RequestHeader}
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/note/77/archieve
	 */
	@PatchMapping("{id}/archieve")
	public ResponseEntity<Response> archieveNote(@PathVariable("id") long noteId,
			@RequestHeader("token") String token) {
		if (noteService.archieveNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note archieved", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Already archived", Util.BAD_REQUEST_RESPONSE_CODE));
	}

	/**
	 * This function takes noteId as {@link PathVariable} and token as
	 * {@link RequestHeader} and verify originality of client
	 * {@link NoteServiceImpl} and after pin operation of note accordingly it
	 * returns the response.
	 * 
	 * @param noteId as {@link PathVariable}
	 * @param token  as {@link RequestHeader}
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/note/78/pin
	 */
	@PatchMapping("{id}/pin")
	public ResponseEntity<Response> pinNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (noteService.pinNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note pinned", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Already pinned", Util.BAD_REQUEST_RESPONSE_CODE));
	}

	/**
	 * This function takes noteId as {@link PathVariable} and token as
	 * {@link RequestHeader} and verify originality of client
	 * {@link NoteServiceImpl} and after trash operation of note accordingly it
	 * returns the response.
	 * 
	 * @param noteId as {@link PathVariable}
	 * @param token  as {@link RequestHeader}
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/note/91/trash
	 */
	@DeleteMapping("{id}/trash")
	public ResponseEntity<Response> trashNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (noteService.trashNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note trashed", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Already trashed", Util.BAD_REQUEST_RESPONSE_CODE));
	}

	/**
	 * This function takes authentication token as {@link RequestHeader} and verify
	 * originality of client {@link NoteServiceImpl} after verification allows user
	 * to get all notes which are not trashed.
	 * 
	 * @param token as {@link RequestHeader}
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/note/fetch/notes
	 */
	@GetMapping("fetch/notes")
	public ResponseEntity<Response> fetchNotes(@RequestHeader("token") String token) {
		List<Note> notes = noteService.getallNotes(token);
		if (!notes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Notes are", Util.OK_RESPONSE_CODE, notes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(Util.NO_NOTES_FOUND_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE));
	}

	/**
	 * This function takes authentication token as {@link RequestHeader} and verify
	 * originality of user {@link NoteServiceImpl} after verification allows user to
	 * get all trashed notes.
	 * 
	 * @param token as {@link RequestHeader}
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/note/fetch/notes/trashed
	 */
	@GetMapping("fetch/notes/trashed")
	public ResponseEntity<Response> fetchTrashedNotes(@RequestHeader("token") String token) {
		List<Note> trashedNotes = noteService.getAllTrashedNotes(token);
		if (!trashedNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Trashed notes are", Util.OK_RESPONSE_CODE, trashedNotes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(Util.NO_NOTES_FOUND_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE));
	}

	/**
	 * This function takes authentication token as {@link RequestHeader} and verify
	 * originality of client {@link NoteServiceImpl} after verification allows user
	 * to get all notes which are pinned.
	 * 
	 * @param token as {@link RequestHeader}
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/note/fetch/notes/pinned
	 */
	@GetMapping("fetch/notes/pinned")
	public ResponseEntity<Response> fetchPinnedNotes(@RequestHeader("token") String token) {
		List<Note> pinnedNotes = noteService.getAllPinnedNotes(token);
		if (!pinnedNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Pinned notes are", Util.OK_RESPONSE_CODE, pinnedNotes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(Util.NO_NOTES_FOUND_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE));
	}

	/**
	 * This function takes authentication token as {@link RequestHeader} and verify
	 * originality of client {@link NoteServiceImpl} after verification allows user
	 * to get all notes which are archived.
	 * 
	 * @param token as {@link RequestHeader}
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/note/fetch/notes/archived
	 */
	@GetMapping("fetch/notes/archived")
	public ResponseEntity<Response> fetchArchivedNotes(@RequestHeader("token") String token) {
		List<Note> archivedNotes = noteService.getAllArchivedNotes(token);
		if (!archivedNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Archived notes are", Util.OK_RESPONSE_CODE, archivedNotes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(Util.NO_NOTES_FOUND_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE));
	}

	/**
	 * This function takes authentication token as {@link RequestHeader} and verify
	 * originality of client {@link NoteServiceImpl} after verification allows user
	 * to change color of the personalized note.
	 * 
	 * @param token      as {@link RequestHeader}
	 * @param noteId     as {@link PathVariable}
	 * @param noteColour as {@link RequestParam}
	 * @return ResponseEntity<Response>
	 * @URL -> http://localhost:8080/note/88/colour?color=red
	 */
	@PostMapping("{id}/colour")
	public ResponseEntity<Response> changeColour(@RequestHeader("token") String token, @PathVariable("id") long noteId,
			@RequestParam("color") String noteColour) {
		noteService.changeColour(token, noteId, noteColour);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("color changed", Util.OK_RESPONSE_CODE));
	}

	/**
	 * This function takes authentication token as {@link RequestHeader} and verify
	 * originality of client {@link NoteServiceImpl} after verification allows user
	 * to add remainder to the personalized note.
	 * 
	 * @param token        as {@link RequestHeader}
	 * @param noteId       as {@link PathVariable}
	 * @param remainderDTO as {@link RequestBody RemainderDTO}
	 * @return ResponseEntity<Response>
	 * @URL -> http://localhost:8080/note/41/remainder/add
	 */
	@PutMapping("{id}/remainder/add")
	public ResponseEntity<Response> setRemainder(@RequestHeader("token") String token, @PathVariable("id") long noteId,
			@RequestBody RemainderDTO remainderDTO) {
		noteService.setRemainderforNote(token, noteId, remainderDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("remainder created", Util.CREATED_RESPONSE_CODE));
	}

	/**
	 * This function takes authentication token as {@link RequestHeader} and verify
	 * originality of client {@link NoteServiceImpl} after verification allows user
	 * to remove the remainder from the personalized note.
	 * 
	 * @param token  as {@link RequestHeader}
	 * @param noteId as {@link PathVariable}
	 * @return ResponseEntity<Response>
	 * @URL -> http://localhost:8080/note/79/remainder/remove
	 */
	@DeleteMapping("{id}/remainder/remove")
	public ResponseEntity<Response> removeRemainder(@RequestHeader("token") String token,
			@PathVariable("id") long noteId) {
		noteService.removeRemainderforNote(token, noteId);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("remainder removed", Util.CREATED_RESPONSE_CODE));
	}

	/**
	 * This function takes authentication token as {@link RequestHeader} and verify
	 * originality of client {@link NoteServiceImpl} after verification allows user
	 * to search for notes based on title.
	 * 
	 * @param token     as {@link RequestHeader}
	 * @param noteTitle as as {@link RequestParam}
	 * @return ResponseEntity<Response>
	 * @URL -> http://localhost:8080/note/search?title=note1
	 */
	@GetMapping("search")
	public ResponseEntity<Response> searchByTitle(@RequestHeader("token") String token,
			@RequestParam("title") String noteTitle) {
		List<Note> fetchedNotes = noteService.searchByTitle(token, noteTitle);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response("found note", Util.OK_RESPONSE_CODE, fetchedNotes));
	}

}
