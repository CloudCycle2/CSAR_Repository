package org.opentosca.csarrepo.model.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.WineryServer;

/**
 * Class to avoid direct access of the hibernate active records for WineryServer
 *
 */
public class WineryServerRepository {

	/**
	 * Returns a WineryServer for the given id.
	 * 
	 * @param id
	 * @return WineryServer
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public WineryServer getbyId(long id) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		WineryServer wineryServer = null;
		try {
			tx = session.beginTransaction();
			wineryServer = (WineryServer) session.get(WineryServer.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return wineryServer;
	}

	/**
	 * Gets all WineryServers.
	 * 
	 * @return List of WineryServers.
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public List<WineryServer> getAll() throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		List<WineryServer> wineryServerList = null;
		try {
			tx = session.beginTransaction();
			// TODO: use another query method with .class instead of String
			wineryServerList = session.createQuery("from WineryServer").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return wineryServerList;
	}

	/**
	 * @param WineryServer
	 *            to be stored
	 * @return id of the saved WineryServer
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public long save(WineryServer wineryServer) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(wineryServer);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return wineryServer.getId();
	}

	/**
	 * @param wineryServer
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public void delete(WineryServer wineryServer) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(wineryServer);
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
			Criteria criteria = session.createCriteria(WineryServer.class);
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
