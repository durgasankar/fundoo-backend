package com.bridgelabz.fundoonotes.repository.implementation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.repository.INoteRepository;

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
@SuppressWarnings({ "rawtypes", "unchecked" })
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
	public Note saveOrUpdate(Note newNote) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(newNote);
		return newNote;
	}

	/**
	 * The EntityManager and the EntityManagerFactory provide an unwrap method which
	 * returns the corresponding classes of the JPA implementation and by using HQL
	 * customized query from current session and fetching operation is carried out
	 * which returns the unique {@link Note} from data from database.
	 */
	@Override
	@Transactional
	public Note getNote(long noteId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE noteId=:id");
		query.setParameter("id", noteId);
		return (Note) query.uniqueResult();
	}

	/**
	 * The EntityManager and the EntityManagerFactory provide an unwrap method which
	 * returns the corresponding classes of the JPA implementation and by using HQL
	 * customized query from current session and fetching operation is carried out
	 * which returns boolean value after deleting the data from database.
	 */

	@Override
	@Transactional
	public boolean isDeletedNote(long noteId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("DELETE FROM Note WHERE noteId=:id");
		query.setParameter("id", noteId);
		query.executeUpdate();
		return true;
	}

	/**
	 * The EntityManager and the EntityManagerFactory provide an unwrap method which
	 * returns the corresponding classes of the JPA implementation and by using HQL
	 * customized query from current session and if the notes are not trashed and
	 * archived then simply fetch them.
	 */
	@Transactional
	@Override
	public List<Note> getAllNotes(long userId) {
		return entityManager.unwrap(Session.class)
				.createQuery("FROM Note WHERE user_id=:id and is_trashed=false and is_archived=false")
				.setParameter("id", userId).getResultList();

	}

	/**
	 * The EntityManager and the EntityManagerFactory provide an unwrap method which
	 * returns the corresponding classes of the JPA implementation and by using HQL
	 * customized query from current session and if the notes are not trashed then
	 * simply fetch them.
	 */
	@Override
	public List<Note> getAllTrashedNotes(long userId) {
		return entityManager.unwrap(Session.class).createQuery("FROM Note WHERE user_id=:id and is_trashed=true")
				.setParameter("id", userId).getResultList();
	}

	/**
	 * The EntityManager and the EntityManagerFactory provide an unwrap method which
	 * returns the corresponding classes of the JPA implementation and by using HQL
	 * customized query from current session and if the notes are not pinned then
	 * simply fetch them.
	 */
	@Override
	public List<Note> getAllPinnedNotes(long userId) {
		return entityManager.unwrap(Session.class).createQuery("FROM Note WHERE user_id=:id and is_pinned=true")
				.setParameter("id", userId).getResultList();
	}

	/**
	 * The EntityManager and the EntityManagerFactory provide an unwrap method which
	 * returns the corresponding classes of the JPA implementation and by using HQL
	 * customized query from current session and if the notes are not archived then
	 * simply fetch them.
	 */
	@Override
	public List<Note> getAllArchivedNotes(long userId) {
		return entityManager.unwrap(Session.class).createQuery("FROM Note WHERE user_id=:id and is_archived=true")
				.setParameter("id", userId).getResultList();
	}

	@Override
	public List<Note> searchBy(String noteTitle) {
		return entityManager.unwrap(Session.class).createQuery("FROM Note WHERE title=:title and is_trashed=false")
				.setParameter("title", noteTitle).getResultList();
	}

}
