package com.bridgeLabz.fundooNotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Opps... Error creating note", 500));
	}

	@PutMapping("update")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDTO noteDto, @RequestParam long noteId,
			@RequestHeader("token") String token) {
		if (noteService.updateNote(noteDto, noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note updated", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Opps...Error updating note", 502));

	}

	@PostMapping("delete/{noteId}")
	public ResponseEntity<Response> deleteNote(@PathVariable long noteId, @RequestHeader("token") String token) {
		if (noteService.deleteNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note deleted", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Opps...Error deleting note", 502));

	}

	@PostMapping("archieve/{noteId}")
	public ResponseEntity<Response> archieveNote(@PathVariable long noteId, @RequestHeader("token") String token) {
		if (noteService.archieveNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note archieved", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Opps...Already archived", 502));
	}

	@PostMapping("pin/{noteId}")
	public ResponseEntity<Response> pinNote(@PathVariable long noteId, @RequestHeader("token") String token) {
		if (noteService.pinNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note pinned", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response("Opps...Already pinned", 502));
	}
	@PostMapping("trash/{id}")
	public ResponseEntity<Response> trashNote(@PathVariable long noteId, @RequestHeader("token") String token) {
		if (noteService.trashNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note pinned", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response("Opps...Error pinning note", 400));
	}
	

}
