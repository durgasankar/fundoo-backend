package com.bridgeLabz.fundooNotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeLabz.fundooNotes.model.dto.NoteDTO;
import com.bridgeLabz.fundooNotes.response.Response;
import com.bridgeLabz.fundooNotes.service.INoteService;

@RestController
@RequestMapping("note")
public class NoteController {

	@Autowired
	private INoteService noteService;

	@PostMapping("create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO noteDto, @RequestHeader String token) {
		if (noteService.createNote(noteDto, token)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note created", 200));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new Response("Opps... Error creating note", 400));

	}

}
