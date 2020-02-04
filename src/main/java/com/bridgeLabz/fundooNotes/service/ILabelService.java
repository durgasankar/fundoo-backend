package com.bridgeLabz.fundooNotes.service;

import java.util.List;

import com.bridgeLabz.fundooNotes.model.Label;
import com.bridgeLabz.fundooNotes.model.dto.LabelDTO;

public interface ILabelService {
	
	public void createLabel(String token, LabelDTO labelDTO);
	
	public boolean createLabelAndMap(String token, long noteId,LabelDTO labelDTO);

	public boolean addNoteToLabel(String token, long noteId, long labelId);

	public boolean removeNoteFromLabel(String token, long noteId, long labelId);

	public boolean isLabelEdited(String token, LabelDTO labelDTO, long labelId);

	public boolean idDeletedLabel(String token, long labelId);

	public List<Label> foundLabelsList(String token);
	
}
