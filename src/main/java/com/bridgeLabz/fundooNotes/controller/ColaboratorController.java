package com.bridgeLabz.fundooNotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeLabz.fundooNotes.response.Response;
import com.bridgeLabz.fundooNotes.service.IColaboratorService;
import com.bridgeLabz.fundooNotes.utility.Util;

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

}
