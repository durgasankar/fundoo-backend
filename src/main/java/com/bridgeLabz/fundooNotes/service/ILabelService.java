package com.bridgeLabz.fundooNotes.service;

import com.bridgeLabz.fundooNotes.model.dto.LabelDTO;

public interface ILabelService {
	
	public boolean createLabel(String token, LabelDTO labelDTO);
	
}
