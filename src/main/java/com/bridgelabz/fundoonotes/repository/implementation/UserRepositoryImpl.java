package com.bridgelabz.fundoonotes.repository.implementation;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.model.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.repository.IUserRepository;

/**
 * This class implements {@link IUserRepository} and defines the implementation
 * functionality of adding available methods in the interface which uses The
 * EntityManager and the EntityManagerFactory provide an unwrap method which
 * returns the corresponding classes of the JPA implementation.
 * loginInformation.getEmailId()
 * 
 * @author Duragasankar Mishra
 * @created 2020-01-21
 * @version 1.0
 */
@Repository
@SuppressWarnings("rawtypes")
public class UserRepositoryImpl implements IUserRepository {

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
	public User save(User newUser) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(newUser);
		return newUser;
	}

	/**
	 * The EntityManager and the EntityManagerFactory provide an unwrap method which
	 * returns the corresponding classes of the JPA implementation and by using HQL
	 * customized query from current session and update operation is carried out
	 * which returns the user after data from database
	 */
	@Override
	public User getUser(String emailId) {
		Session session = entityManager.unwrap(Session.class);
		Query emailFetchQuery = session.createQuery("FROM User where emailId=:emailId");
		emailFetchQuery.setParameter("emailId", emailId);
		return (User) emailFetchQuery.uniqueResult();
	}

	/**
	 * The EntityManager and the EntityManagerFactory provide an unwrap method which
	 * returns the corresponding classes of the JPA implementation and by using HQL
	 * customized query from current session and fetching operation is carried out
	 * which returns the user after data from database.
	 */
	@Override
	@Transactional
	public User getUser(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery(" FROM User WHERE id=:id");
		query.setParameter("id", id);
		return (User) query.uniqueResult();
	}

	/**
	 * The EntityManager and the EntityManagerFactory provide an unwrap method which
	 * returns the corresponding classes of the JPA implementation and by using HQL
	 * customized query from current session and update operation is carried out
	 * which returns boolean value after successful completion of update operation.
	 */
	@Override
	@Transactional
	public boolean isVerifiedUser(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("UPDATE User set is_verified=:verified" + " WHERE id=:id");
		query.setParameter("verified", true);
		query.setParameter("id", id);
		query.executeUpdate();
		return true;
	}

	/**
	 * This function takes {@link UpdatePassword} and id of the user as Long input
	 * Parameter and The EntityManager and the EntityManagerFactory provide an
	 * unwrap method which returns the corresponding classes of the JPA
	 * implementation and by using HQL customized query from current session and
	 * update the given input information with the database and after successful
	 * update it returns boolean value.
	 */
	@Transactional
	@Override
	public boolean updatePassword(UpdatePassword updatePasswordinformation, long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("UPDATE User set password=:updatedPassword" + " WHERE id=:id");
		query.setParameter("updatedPassword", updatePasswordinformation.getConfirmPassword());
		query.setParameter("id", id);
		query.executeUpdate();
		return true;
	}

}