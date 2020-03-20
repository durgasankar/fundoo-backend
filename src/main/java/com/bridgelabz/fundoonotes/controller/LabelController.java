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
import com.bridgelabz.fundoonotes.model.dto.LabelDTO;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ILabelService;
import com.bridgelabz.fundoonotes.utility.Util;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Durgasankar Mishra
 * @created 2020-03-01
 * @version 1.0
 *
 */
@RestController
@RequestMapping("labels")
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
	@ApiOperation(value = "Api to create a lebel and add a note", response = Response.class)
	public ResponseEntity<Response> createandMapLabel(@RequestHeader("token") String token,
			@RequestBody LabelDTO labelDTO, @PathVariable("noteId") long noteId) {
		labelService.createLabelAndMap(token, noteId, labelDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created and mapped", 201, labelDTO));
	}

	@PostMapping("/map")
	@ApiOperation(value = "Api to add note to existing label", response = Response.class)
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

	// URL -> http://localhost:8080/label/5/delete
	@DeleteMapping("/{labelId}/delete")
	@ApiOperation(value = "Api to delete a particular label", response = Response.class)
	public ResponseEntity<Response> deleteLabel(@RequestHeader("token") String token,
			@PathVariable("labelId") long labelId) {
		if (labelService.idDeletedLabel(token, labelId)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("label deleted sucessfully", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Error deleting label", Util.BAD_REQUEST_RESPONSE_CODE));
	}

	@GetMapping("/fetch")
	@ApiOperation(value = "Api to delete a particular label", response = Response.class)
	public ResponseEntity<Response> getAllLabels(@RequestHeader("token") String token) {
		List<Label> foundLabelList = labelService.listOfLabels(token);
		if (!foundLabelList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("found labels", 200, foundLabelList));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response("Opps...No labels found", Util.NOT_FOUND_RESPONSE_CODE));
	}

	@GetMapping("/fetch/{labelId}")
	@ApiOperation(value = "Api to fetch all notes of a particular label", response = Response.class)
	public ResponseEntity<Response> getAllNotesOfLabel(@RequestHeader("token") String token,
			@PathVariable("labelId") long labelId) {
		List<Note> foundNotesOfLabelList = labelService.listOfNotesOfLabel(token, labelId);
		if (!foundNotesOfLabelList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("found notes", 200, foundNotesOfLabelList));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response("Opps...No Notes founds", Util.NOT_FOUND_RESPONSE_CODE));
	}

}
