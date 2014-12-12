package org.opentosca.csarrepo.model.repository;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.opentosca.csarrepo.model.Csar;

/**
 * Class to avoid direct access of the hibernate active records for CSAR
 * 
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
public class CsarRepository {

	/**
	 * Returns csar matching the given id
	 * 
	 * @param id
	 * @return csar
	 * @throws Exception
	 */
	public Csar getbyId(long id) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		Csar csar = null;
		try {
			tx = session.beginTransaction();
			csar = (Csar) session.get(Csar.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new Exception(e);
		} finally {
			session.close();
		}

		return csar;
	}

	/**
	 * Gets all csars
	 * 
	 * @return List of csars
	 * @throws Exception
	 */
	public List<Csar> getAll() throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		List<Csar> csarList = null;
		try {
			tx = session.beginTransaction();
			csarList = session.createQuery("from Csar").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new Exception(e);
		} finally {
			session.close();
		}
		return csarList;
	}

	/**
	 * @param csar
	 *            to be stored
	 * @return id of the just saved csar
	 * @throws Exception
	 *             upon problems committing the underlying transaction
	 */
	public long save(Csar csar) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(csar);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new Exception(e);
		} finally {
			session.close();
		}
		return csar.getId();
	}

	/**
	 * @param csar
	 * @throws Exception
	 *             upon problems committing the underlying transaction
	 */
	public void delete(Csar csar) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(csar);
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