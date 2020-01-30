package com.bridgeLabz.fundooNotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.bridgeLabz.fundooNotes.model.dto.UserDTO;
import com.bridgeLabz.fundooNotes.response.Response;
import com.bridgeLabz.fundooNotes.service.INoteService;
import com.bridgeLabz.fundooNotes.service.Implementation.NoteServiceImpl;

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

	private static final String EMPTY_CONTENT_LIST_MESSAGE = "Opps...No notes Found!";
	private static final int BAD_REQUEST_RESPONSE_CODE = 400;
	private static final int OK_RESPONSE_CODE = 200;
	private static final int NOT_FOUND_RESPONSE_CODE = 404;
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
	 */
	@PostMapping("create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO noteDto, @RequestHeader("token") String token) {
		if (noteService.createNote(noteDto, token)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 201));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps... Error creating note", BAD_REQUEST_RESPONSE_CODE));
	}

	/**
	 * This function takes {@link NoteDTO} as request body and token from
	 * {@link RequestHeader} and verify originality of client
	 * {@link NoteServiceImpl} and after update accordingly it returns the response.
	 * 
	 * @param noteDto as {@link UserDTO}
	 * @param token   as String input parameter
	 * @return ResponseEntity<Response>
	 */
	@PutMapping("update")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDTO noteDto, @RequestParam long noteId,
			@RequestHeader("token") String token) {
		if (noteService.updateNote(noteDto, noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note updated", OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Error updating note", BAD_REQUEST_RESPONSE_CODE));

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
	 */
	@PostMapping("delete/{noteId}")
	public ResponseEntity<Response> deleteNote(@PathVariable long noteId, @RequestHeader("token") String token) {
		if (noteService.deleteNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note deleted", OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Error deleting note", BAD_REQUEST_RESPONSE_CODE));

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
	 */
	@PostMapping("archieve/{id}")
	public ResponseEntity<Response> archieveNote(@PathVariable("id") long noteId,
			@RequestHeader("token") String token) {
		if (noteService.archieveNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note archieved", OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Already archived", BAD_REQUEST_RESPONSE_CODE));
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
	 */
	@PostMapping("pin/{id}")
	public ResponseEntity<Response> pinNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (noteService.pinNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note pinned", OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Already pinned", BAD_REQUEST_RESPONSE_CODE));
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
	 */
	@PostMapping("trash/{id}")
	public ResponseEntity<Response> trashNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (noteService.trashNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note trashed", OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Already trashed", BAD_REQUEST_RESPONSE_CODE));
	}

	@GetMapping("fetch/notes")
	public ResponseEntity<Response> fetchNotes(@RequestHeader("token") String token) {
		List<Note> notes = noteService.getallNotes(token);
		if (!notes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Notes are", OK_RESPONSE_CODE, notes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(EMPTY_CONTENT_LIST_MESSAGE, NOT_FOUND_RESPONSE_CODE));
	}

	@GetMapping("fetch/notes/trashed")
	public ResponseEntity<Response> fetchTrashedNotes(@RequestHeader("token") String token) {
		List<Note> trashedNotes = noteService.getAllTrashedNotes(token);
		if (!trashedNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Trashed notes are", OK_RESPONSE_CODE, trashedNotes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(EMPTY_CONTENT_LIST_MESSAGE, NOT_FOUND_RESPONSE_CODE));
	}

	@GetMapping("fetch/notes/pinned")
	public ResponseEntity<Response> fetchPinnedNotes(@RequestHeader("token") String token) {
		List<Note> pinnedNotes = noteService.getAllPinnedNotes(token);
		if (!pinnedNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Pinned notes are", OK_RESPONSE_CODE, pinnedNotes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(EMPTY_CONTENT_LIST_MESSAGE, NOT_FOUND_RESPONSE_CODE));
	}

}
