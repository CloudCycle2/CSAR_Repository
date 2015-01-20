package org.opentosca.csarrepo.model.repository;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.OpenToscaServer;

public class OpenToscaServerRepository {

	/**
	 * Returns an OpenTosca Server for the given id.
	 * 
	 * @param id
	 * @return OpenToscaServer
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public OpenToscaServer getbyId(long id) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		OpenToscaServer otServer = null;
		try {
			tx = session.beginTransaction();
			otServer = (OpenToscaServer) session.get(OpenToscaServer.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return otServer;
	}

	/**
	 * Gets all OpenToscaServers.
	 * 
	 * @return List of all OpenToscaServers
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public List<OpenToscaServer> getAll() throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		List<OpenToscaServer> openToscaServerList = null;
		try {
			tx = session.beginTransaction();
			openToscaServerList = session.createQuery("from OpenToscaServer").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return openToscaServerList;
	}

	/**
	 * @param OpenToscaServer
	 *            to be stored
	 * @return id of the saved OpenToscaServer
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public long save(OpenToscaServer otServer) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(otServer);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return otServer.getId();
	}

	/**
	 * @param OpenToscaServer
	 * @throws Exception
	 *             upon problems committing the underlying transaction
	 */
	public void delete(OpenToscaServer otServer) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(otServer);
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

}

