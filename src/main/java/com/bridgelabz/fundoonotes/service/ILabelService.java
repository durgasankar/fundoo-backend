package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.model.dto.LabelDTO;

public interface ILabelService {
	
	public void createLabel(String token, LabelDTO labelDTO);
	
	public boolean createLabelAndMap(String token, long noteId,LabelDTO labelDTO);

	public boolean addNoteToLabel(String token, long noteId, long labelId);

	public boolean removeNoteFromLabel(String token, long noteId, long labelId);

	public boolean isLabelEdited(String token, String labelName, long labelId);

	public boolean idDeletedLabel(String token, long labelId);

	public List<Label> listOfLabels(String token);

	public List<Note> listOfNotesOfLabel(String token, long labelId);
	
}
