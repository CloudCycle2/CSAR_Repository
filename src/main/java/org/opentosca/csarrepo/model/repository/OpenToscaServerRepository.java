package org.opentosca.csarrepo.model.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.CsarFile;
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
			Criteria criteria = session.createCriteria(OpenToscaServer.class);
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

