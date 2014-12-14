package org.opentosca.csarrepo.model.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.opentosca.csarrepo.model.CsarFile;

/**
 * Class to avoid direct access of the hibernate active records for CSAR file.
 * 
 * @author Markus Eisele, Dennis Przytarski, Nikolai Neugebauer
 *
 */
public class CsarFileRepository {

	/**
	 * Returns a csar file for the given id.
	 * 
	 * @param id
	 * @return CsarFile
	 */
	public CsarFile getbyId(long id) throws Exception {
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
			throw new Exception(e);
		} finally {
			session.close();
		}
		return csarFile;
	}

	/**
	 * Gets all CSAR files.
	 * 
	 * @return List of CSAR files.
	 */
	public List<CsarFile> getAll() throws Exception {
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
			throw new Exception(e);
		} finally {
			session.close();
		}
		return csarFileList;
	}

	/**
	 * @param CsarFile
	 *            to be stored
	 * @return id of the saved csar file
	 * @throws Exception
	 *             upon problems commiting the underlying transaction
	 */
	public long save(CsarFile csarFile) throws Exception {
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
			throw new Exception(e);
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
	public void delete(CsarFile csarFile) throws Exception {
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
			throw new Exception(e);
		} finally {
			session.close();
		}
	}
}
