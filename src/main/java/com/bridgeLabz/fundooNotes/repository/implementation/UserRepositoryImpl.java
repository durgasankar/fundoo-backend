package com.bridgeLabz.fundooNotes.repository.implementation;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeLabz.fundooNotes.model.User;
import com.bridgeLabz.fundooNotes.repository.IUserRepository;

@Repository
@SuppressWarnings("rawtypes")
public class UserRepositoryImpl implements IUserRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	public User save(User newUser) {
		// got the current session from entity manager
		Session session = entityManager.unwrap(Session.class);
		// from the existing session did hibernate operation
		session.saveOrUpdate(newUser);
		return newUser;
	}

	@Override
	public User getUser(String emailId) {
		Session session = entityManager.unwrap(Session.class);
		Query emailFetchQuery = session.createQuery("FROM User where emailId=:emailId");
		emailFetchQuery.setParameter("emailId", emailId);
		return (User) emailFetchQuery.uniqueResult();
	}

	@Override
	public User getUser(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery(" FROM User where id=:id");
		query.setParameter("id", id);
		return (User) query.uniqueResult();
	}

	@Override
	@Transactional
	public boolean isVerifiedUser(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("update User set is_verified=:verified" + " where id=:id");
		query.setParameter("verified", true);
		query.setParameter("id", id);
		int affectedRows = query.executeUpdate();
		if (affectedRows > 0)
			return true;
		return false;
	}

}