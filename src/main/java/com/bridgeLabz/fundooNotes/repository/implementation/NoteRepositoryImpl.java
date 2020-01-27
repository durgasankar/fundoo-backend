package com.bridgeLabz.fundooNotes.repository.implementation;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeLabz.fundooNotes.model.Note;
import com.bridgeLabz.fundooNotes.repository.INoteRepository;

/**
 * This class implements {@link INoteRepository} and defines the implementation
 * functionality of adding available methods in the interface which uses The
 * EntityManager and the EntityManagerFactory provide an unwrap method which
 * returns the corresponding classes of the JPA implementation.
 * 
 * @author Duragasankar Mishra
 * @created 2020-01-27
 * @version 1.0
 */
@Repository
public class NoteRepositoryImpl implements INoteRepository {

	@Autowired
	private EntityManager entityManager;

	/**
	 * The EntityManager and the EntityManagerFactory provide an unwrap method which
	 * returns the corresponding classes of the JPA implementation then access the
	 * save functionality and after successfully saving the data in the database
	 * returns the data which is saved in the database.
	 */
	@Override
	@Transactional
	public Note save(Note newNote) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(newNote);
		return newNote;
	}

}
