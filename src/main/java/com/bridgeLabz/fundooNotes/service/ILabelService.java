package com.bridgeLabz.fundooNotes.service;

import com.bridgeLabz.fundooNotes.model.dto.LabelDTO;

public interface ILabelService {
	
	public void createLabel(String token, LabelDTO labelDTO);
	
	public boolean createLabelAndMap(String token, long noteId,LabelDTO labelDTO);

	public boolean addNoteToLabel(String token, long noteId, long labelId);
	
}
