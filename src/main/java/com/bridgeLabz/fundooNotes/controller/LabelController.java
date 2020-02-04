package com.bridgeLabz.fundooNotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeLabz.fundooNotes.model.dto.LabelDTO;
import com.bridgeLabz.fundooNotes.response.Response;
import com.bridgeLabz.fundooNotes.service.ILabelService;
import com.bridgeLabz.fundooNotes.utility.Util;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("label")
public class LabelController {

	@Autowired
	private ILabelService labelService;

	@PostMapping("/create")
	@ApiOperation(value = "Api to create label", response = Response.class)
	public ResponseEntity<Response> createLabel(@RequestHeader("token") String token, @RequestBody LabelDTO labelDTO) {
		labelService.createLabel(token, labelDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created", 201, labelDTO));
	}

	@PostMapping("/create/{noteId}")
	@ApiOperation(value = "Api to create and add label with note", response = Response.class)
	public ResponseEntity<Response> createandMapLabel(@RequestHeader("token") String token,
			@RequestBody LabelDTO labelDTO, @PathVariable("noteId") long noteId) {
		labelService.createLabelAndMap(token, noteId, labelDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created and mapped", 201, labelDTO));
	}

	@PostMapping("/addlabels")
	@ApiOperation(value = "Api to add existing label with note", response = Response.class)
	public ResponseEntity<Response> addLabelsToNote(@RequestHeader("token") String token,
			@RequestParam("noteId") long noteId, @RequestParam("labelId") long labelId) {
		labelService.addNoteToLabel(token, noteId, labelId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note added to the label", 200));
	}

	@PatchMapping("/remove")
	@ApiOperation(value = "Api to remove note from label", response = Response.class)
	public ResponseEntity<Response> removeLabelsToNote(@RequestHeader("token") String token,
			@RequestParam("noteId") long noteId, @RequestParam("labelId") long labelId) {
		labelService.removeNoteFromLabel(token, noteId, labelId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("note removed from the label", 200));
	}

	@PutMapping("/edit")
	@ApiOperation(value = "Api to edit the name of label", response = Response.class)
	public ResponseEntity<Response> editLabelName(@RequestHeader("token") String token, @RequestBody LabelDTO labelDTO,
			@RequestParam("labelId") long labelId) {
		if (labelService.isLabelEdited(token, labelDTO, labelId)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("label name changed", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...new Label name can't be same!", Util.BAD_REQUEST_RESPONSE_CODE));
	}

}
