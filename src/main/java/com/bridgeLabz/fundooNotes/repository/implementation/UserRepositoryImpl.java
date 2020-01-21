package com.bridgeLabz.fundooNotes.repository.implementation;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeLabz.fundooNotes.model.User;
import com.bridgeLabz.fundooNotes.repository.IUserRepository;

@Repository
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
		Query emailFetchQuery = session.createQuery("FROM User where email=:emailId");
		emailFetchQuery.setParameter("email", emailId);
		return (User) emailFetchQuery.uniqueResult();
	}

}