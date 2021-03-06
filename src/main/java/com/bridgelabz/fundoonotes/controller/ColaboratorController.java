package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.IColaboratorService;
import com.bridgelabz.fundoonotes.utility.Util;

/**
 * 
 * @author Durgasankar Mishra
 * @created 2020-02-09
 * @version 1.0
 */
@RestController
@RequestMapping("/colaborators")
public class ColaboratorController {

	@Autowired
	private IColaboratorService colaboratorService;

	@PostMapping("/{noteId}")
	public ResponseEntity<Response> addColaborator(@RequestHeader("token") String token,
			@PathVariable("noteId") long noteId, @RequestParam("emailId") String emailId) {
		if (colaboratorService.addColaborator(token, noteId, emailId)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("colaborator added.", 202, emailId));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Error adding colborator!", Util.BAD_REQUEST_RESPONSE_CODE));

	}

	@GetMapping("/{noteId}")
	public ResponseEntity<Response> getColaborators(@RequestHeader("token") String token,
			@PathVariable("noteId") long noteId) {
		List<User> fetchedColaborators = colaboratorService.getColaboratorsOfNote(token, noteId);
		if (!fetchedColaborators.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("colaborators : ", Util.OK_RESPONSE_CODE, fetchedColaborators));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(Util.NO_NOTES_FOUND_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE));
	}

	@DeleteMapping("/{noteId}")
	public ResponseEntity<Response> deleteColaborator(@RequestHeader("token") String token,
			@PathVariable("noteId") long noteId, @RequestParam("emailId") String emailId) {
		if (!colaboratorService.removeColaborator(token, noteId, emailId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Response("Opps...Error removing Colaborator!", Util.BAD_REQUEST_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("colaborator removed.", 202, emailId));
	}
}