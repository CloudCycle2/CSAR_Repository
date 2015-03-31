package org.opentosca.csarrepo.model.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.WineryServer;

/**
 * Class to avoid direct access of the hibernate active records for CSAR file.
 * 
 * @author Markus Eisele, Dennis Przytarski
 *
 */
public class CsarFileRepository {

	/**
	 * Returns a csar file for the given id.
	 * 
	 * @param id
	 * @return CsarFile
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public CsarFile getbyId(long id) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		CsarFile csarFile = null;
		try {
			tx = session.beginTransaction();
			csarFile = (CsarFile) session.get(CsarFile.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return csarFile;
	}

	/**
	 * Gets all CSAR files.
	 * 
	 * @return List of CSAR files.
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public List<CsarFile> getAll() throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		List<CsarFile> csarFileList = null;
		try {
			tx = session.beginTransaction();
			csarFileList = session.createQuery("from CsarFile").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return csarFileList;
	}

	/**
	 * @param CsarFile
	 *            to be stored
	 * @return id of the saved csar file
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public long save(CsarFile csarFile) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(csarFile);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return csarFile.getId();
	}

	/**
	 * @param CsarFile
	 * @throws Exception
	 *             upon problems committing the underlying transaction
	 */
	public void delete(CsarFile csarFile) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(csarFile);
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
			Criteria criteria = session.createCriteria(CsarFile.class);
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
