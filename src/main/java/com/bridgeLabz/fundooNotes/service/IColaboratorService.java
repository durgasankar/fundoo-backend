package com.bridgeLabz.fundooNotes.service;

import java.util.List;

import com.bridgeLabz.fundooNotes.model.User;

/**
 * 
 * @author Durgasankar Mishra
 * @created 2020-02-09
 * @version 1.0
 */

public interface IColaboratorService {

	public boolean addColaborator(String token, long noteId, String emailId);

	public List<User> getColaboratorsOfNote(String token, long noteId);

	public boolean removeColaborator(String token, long noteId, String emailId);

}
