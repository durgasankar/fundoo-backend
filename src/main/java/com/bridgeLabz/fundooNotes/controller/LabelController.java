package com.bridgeLabz.fundooNotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeLabz.fundooNotes.model.dto.LabelDTO;
import com.bridgeLabz.fundooNotes.response.Response;
import com.bridgeLabz.fundooNotes.service.ILabelService;

@RestController
@RequestMapping("label")
public class LabelController {

	@Autowired
	private ILabelService labelService;

	@PostMapping("create")
	public ResponseEntity<Response> createLabel(@RequestHeader("token") String token, @RequestBody LabelDTO labelDTO) {
		if (labelService.createLabel(token, labelDTO)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created", 200, labelDTO));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("label creation failed", 400));
	}
}
