package org.opentosca.csarrepo.model.repository;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.CsarPlan;

/**
 * Class to avoid direct access of the hibernate active records for CSAR plan.
 * 
 * @author Dennis Przytarski
 *
 */
public class CsarPlanRepository {

	/**
	 * Returns a csar plan for the given id.
	 * 
	 * @param id
	 * @return csarPlan
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public CsarPlan getById(long id) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		CsarPlan csarPlan = null;
		try {
			tx = session.beginTransaction();
			csarPlan = (CsarPlan) session.get(CsarPlan.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return csarPlan;
	}

	/**
	 * Gets all CSAR plans.
	 * 
	 * @return List of CSAR plans.
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public List<CsarPlan> getAll() throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		List<CsarPlan> csarPlanList = null;
		try {
			tx = session.beginTransaction();
			csarPlanList = session.createQuery("from CsarPlan").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return csarPlanList;
	}

	/**
	 * @param csarPlan
	 *            to be stored
	 * @return id of the saved csar plan
	 * @throws PersistenceException
	 *             upon problems committing the underlying transaction
	 */
	public long save(CsarPlan csarPlan) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(csarPlan);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			session.close();
		}
		return csarPlan.getCsarPlanId();
	}

	/**
	 * @param csarPlan
	 * @throws Exception
	 *             upon problems committing the underlying transaction
	 */
	public void delete(CsarPlan csarPlan) throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(csarPlan);
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
