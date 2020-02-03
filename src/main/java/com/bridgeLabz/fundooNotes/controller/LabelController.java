package com.bridgeLabz.fundooNotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeLabz.fundooNotes.model.dto.LabelDTO;
import com.bridgeLabz.fundooNotes.response.Response;
import com.bridgeLabz.fundooNotes.service.ILabelService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("label")
public class LabelController {

	@Autowired
	private ILabelService labelService;

	@PostMapping("create")
	@ApiOperation(value = "Api to create label", response = Response.class)
	public ResponseEntity<Response> createLabel(@RequestHeader("token") String token, @RequestBody LabelDTO labelDTO) {
		labelService.createLabel(token, labelDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created", 201, labelDTO));
	}

	@PostMapping("create/{noteId}")
	@ApiOperation(value = "Api to create and add label with note", response = Response.class)
	public ResponseEntity<Response> createandMapLabel(@RequestHeader("token") String token,
			@RequestBody LabelDTO labelDTO, @PathVariable("noteId") long noteId) {
		labelService.createLabelAndMap(token, noteId, labelDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created and mapped", 201, labelDTO));
	}

	@PostMapping("addlabels")
	@ApiOperation(value = "Api to add existing label with note", response = Response.class)
	public ResponseEntity<Response> addLabelsToNote(@RequestHeader("token") String token,
			@RequestParam("noteId") long noteId, @RequestParam("labelId") long labelId) {
		labelService.addNoteToLabel(token, noteId, labelId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label added to the note", 200));
	}
	
	
	
}
