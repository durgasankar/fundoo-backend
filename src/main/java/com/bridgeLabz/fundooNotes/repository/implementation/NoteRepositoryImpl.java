package com.bridgeLabz.fundooNotes.repository.implementation;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeLabz.fundooNotes.model.Note;
import com.bridgeLabz.fundooNotes.repository.INoteRepository;

@Repository
public class NoteRepositoryImpl implements INoteRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public Note save(Note newNote) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(newNote);
		return newNote;
	}

}
