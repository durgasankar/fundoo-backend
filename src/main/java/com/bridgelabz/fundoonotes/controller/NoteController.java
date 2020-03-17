package com.bridgelabz.fundoonotes.controller;

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

import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.model.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.dto.RemainderDTO;
import com.bridgelabz.fundoonotes.model.dto.UserDTO;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.INoteService;
import com.bridgelabz.fundoonotes.service.implementation.NoteServiceImpl;
import com.bridgelabz.fundoonotes.utility.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
@RequestMapping("notes")
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
	@ApiOperation(value = "create a new note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "note created"),
			@ApiResponse(code = 400, message = "Opps... Error creating note") })
	@PostMapping("create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO noteDto, @RequestHeader("token") String token) {
		if (noteService.createNote(noteDto, token)) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("Note created", Util.CREATED_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps... Error creating note!", Util.BAD_REQUEST_RESPONSE_CODE));
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
	@ApiOperation(value = "update an existing note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "note updated"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 400, message = "Opps...Error updating note!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@PutMapping("update")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDTO noteDto, @RequestParam("id") long noteId,
			@RequestHeader("token") String token) {
		if (noteService.updateNote(noteDto, noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Note updated", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Error updating note!", Util.BAD_REQUEST_RESPONSE_CODE));
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
	@ApiOperation(value = "delete an existing note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "note deleted"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 400, message = "Opps...Error deleting note!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@DeleteMapping("{id}/delete")
	public ResponseEntity<Response> deleteNotePermanently(@PathVariable("id") long noteId,
			@RequestHeader("token") String token) {
		if (noteService.deleteNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Note deleted forever", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
				.body(new Response("Opps...Error deleting note!", Util.BAD_GATEWAY_RESPONSE_CODE));

	}

	/**
	 * This function takes noteId as {@link PathVariable} and token as
	 * {@link RequestHeader} and verify originality of client
	 * {@link NoteServiceImpl} and hence restore operation of note accordingly it
	 * returns the response.
	 * 
	 * @param noteId as {@link PathVariable}
	 * @param token  as {@link RequestHeader}
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/note/76/restore
	 */
	@ApiOperation(value = "delete an existing note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "note deleted"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 400, message = "Opps...Error deleting note!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@PutMapping("{id}/restore")
	public ResponseEntity<Response> restoreFromTrashed(@PathVariable("id") long noteId,
			@RequestHeader("token") String token) {
		if (noteService.restoreNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Note restored", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Error Restoring note!", Util.BAD_REQUEST_RESPONSE_CODE));

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
	@ApiOperation(value = "archive an existing note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "note archived"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 400, message = "Opps...Already archived!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@DeleteMapping("{id}/archive")
	public ResponseEntity<Response> archieveNote(@PathVariable("id") long noteId,
			@RequestHeader("token") String token) {
		if (noteService.isArchivedNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Note archieved", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Note unarchived", Util.CREATED_RESPONSE_CODE));
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
	@ApiOperation(value = "pin/unpin operation of existing note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "note pinned"),
			@ApiResponse(code = 201, message = "note unpinned"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@PatchMapping("{id}/pin")
	public ResponseEntity<Response> pinNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (noteService.isPinnedNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Note pinned", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Note unpinned", Util.CREATED_RESPONSE_CODE));
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
	@ApiOperation(value = "trash operation for an existing note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "note pinned"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 400, message = "Opps...Already trashed!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@DeleteMapping("{id}/trash")
	public ResponseEntity<Response> trashNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (noteService.trashNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Note trashed", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Already trashed!", Util.BAD_REQUEST_RESPONSE_CODE));
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
	public ResponseEntity<Response> getAllNotes(@RequestHeader String token) {
		return ResponseEntity.status(HttpStatus.OK).body(new Response("found", 200, noteService.getallNotes(token)));
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
	@ApiOperation(value = "fetch all trashed notes for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Trashed notes are"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!"),
			@ApiResponse(code = 404, message = "Opps...No notes Found!") })
	@GetMapping("fetch/notes/trashed")
	public ResponseEntity<Response> fetchTrashedNotes(@RequestHeader("token") String token) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response("Trashed notes are", Util.OK_RESPONSE_CODE, noteService.getAllTrashedNotes(token)));
	}

	/**
	 * This function takes authentication token as {@link RequestHeader} and verify
	 * originality of user {@link NoteServiceImpl} after verification allows user to
	 * get all remainder notes.
	 * 
	 * @param token as {@link RequestHeader}
	 * @return ResponseEntity<Response>
	 * @URL http://localhost:8080/note/fetch/notes/remainders
	 */
	@ApiOperation(value = "fetch all remainder notes for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "remainder notes are"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!"),
			@ApiResponse(code = 404, message = "Opps...No notes Found!") })
	@GetMapping("fetch/notes/remainders")
	public ResponseEntity<Response> fetchRemainderNotes(@RequestHeader("token") String token) {
		return ResponseEntity.status(HttpStatus.OK).body(
				new Response("Remainder notes are", Util.OK_RESPONSE_CODE, noteService.getAllRemaindersNotes(token)));
	}

	@GetMapping("fetch/notes/{noteId}/labels")
	public ResponseEntity<Response> fetchLabelsOfNote(@RequestHeader("token") String token,
			@PathVariable("noteId") long noteId) {
		List<Label> fetchedLabels = noteService.getLabelsOfNote(token, noteId);
		if (!fetchedLabels.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Labels are", Util.OK_RESPONSE_CODE, fetchedLabels));
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
				.body(new Response(Util.NO_NOTES_FOUND_MESSAGE, Util.BAD_GATEWAY_RESPONSE_CODE, fetchedLabels));
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
	@ApiOperation(value = "fetch all pinned notes for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Pinned notes are"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!"),
			@ApiResponse(code = 404, message = "Opps...No notes Found!") })
	@GetMapping("fetch/notes/pinned")
	public ResponseEntity<Response> fetchPinnedNotes(@RequestHeader("token") String token) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response("Pinned notes are", Util.OK_RESPONSE_CODE, noteService.getAllPinnedNotes(token)));
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
	@ApiOperation(value = "fetch all archived notes for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Archived notes are"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!"),
			@ApiResponse(code = 404, message = "Opps...No notes Found!") })
	@GetMapping("fetch/notes/archived")
	public ResponseEntity<Response> fetchArchivedNotes(@RequestHeader("token") String token) {
		return ResponseEntity.status(HttpStatus.OK).body(
				new Response("Archived notes are", Util.OK_RESPONSE_CODE, noteService.getAllArchivedNotes(token)));

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
	@ApiOperation(value = "change color of a note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Pinned notes are"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
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
	@ApiOperation(value = "set remainder for a note of valid user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "remainder created"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!"),
			@ApiResponse(code = 502, message = "Opps...Remainder already set!") })
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
	@ApiOperation(value = "remove remainder for a note of valid user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "remainder removed"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!"),
			@ApiResponse(code = 502, message = "Opps...Remainder already removed!") })
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
	@ApiOperation(value = "search operation for note based on title of valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "found notes"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@GetMapping("search")
	public ResponseEntity<Response> searchByTitle(@RequestHeader("token") String token,
			@RequestParam("title") String noteTitle) {
		List<Note> fetchedNotes = noteService.searchByTitle(token, noteTitle);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response("found notes", Util.OK_RESPONSE_CODE, fetchedNotes));
	}

}