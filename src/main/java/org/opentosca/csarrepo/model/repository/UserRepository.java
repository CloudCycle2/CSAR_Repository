package org.opentosca.csarrepo.model.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.User;

/**
 * Class to avoid direct access of the hibernate active records for User file.
 * 
 * @author Markus Eisele, Dennis Przytarski
 *
 */
public class UserRepository {

	/**
	 * Returns a user for the given id.
	 * 
	 * @param id
	 * @return User
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public User getById(long id) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		User user = null;
		try {
			tx = session.beginTransaction();
			user = (User) session.get(User.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return user;
	}

	/**
	 * Gets all users.
	 * 
	 * @return List of User.
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public List<User> getAll() throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		List<User> userList = null;
		try {
			tx = session.beginTransaction();
			userList = session.createQuery("from User").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return userList;
	}

	/**
	 * @param User
	 *            to be stored
	 * @return id of the saved user
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public long save(User user) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return user.getId();
	}

	/**
	 * @param User
	 * @throws Exception
	 *             upon problems committing the underlying transaction
	 */
	public void delete(User user) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
	}

	/**
	 * Get user by name
	 * 
	 * @param name
	 * @return User
	 * @throws PersistenceException
	 */
	public User getByName(String name) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("name", name));
		User user = (User) criteria.uniqueResult();
		session.close();
		return user;
	}
	
	/**
	 * counts the number of available instances
	 * 
	 * @return instance count
	 * @throws PersistenceException
	 * 						upon problems committing the underlying transaction
	 */
	public long count() throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		long count = 0;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(User.class);
			criteria.setProjection(Projections.rowCount());
			count = (Long) criteria.uniqueResult();
			tx.commit();
		} catch (HibernateException e) {
			if(tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		
		return count;
	}

}
