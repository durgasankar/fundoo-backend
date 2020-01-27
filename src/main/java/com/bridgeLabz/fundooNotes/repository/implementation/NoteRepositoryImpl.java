package com.bridgeLabz.fundooNotes.repository.implementation;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeLabz.fundooNotes.model.Note;
import com.bridgeLabz.fundooNotes.repository.INoteRepository;

public class NoteRepositoryImpl implements INoteRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Note save(Note newNote) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(newNote);
		return newNote;
	}

}
