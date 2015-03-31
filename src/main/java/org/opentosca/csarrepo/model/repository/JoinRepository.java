package org.opentosca.csarrepo.model.repository;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.join.CsarFileOpenToscaServer;

/**
 * Class to avoid direct access of the hibernate active records for CSAR file.
 * 
 * @author Marcus Eisele, Dennis Przytarski
 *
 */
public class JoinRepository {

	public void removeCsarFileOpenToscaServer(CsarFile csarFile, OpenToscaServer openToscaServer)
			throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.createQuery(
					"DELETE CsarFileOpenToscaServer map WHERE map.csarFile = :csarFileId AND map.openToscaServer = :openToscaServerId")
					.setString("csarFileId", csarFile.getId() + "")
					.setString("openToscaServerId", openToscaServer.getId() + "").executeUpdate();
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

	public CsarFileOpenToscaServer getCsarFileOpenToscaServer(CsarFile csarFile, OpenToscaServer openToscaServer)
			throws PersistenceException {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Object object = session
					.createQuery(
							"SELECT map FROM CsarFileOpenToscaServer map WHERE map.csarFile = :csarFileId AND map.openToscaServer = :openToscaServerId")
					.setString("csarFileId", csarFile.getId() + "")
					.setString("openToscaServerId", openToscaServer.getId() + "").uniqueResult();
			tx.commit();
			return (CsarFileOpenToscaServer) object;
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
			Criteria criteria = session.createCriteria(CsarFileOpenToscaServer.class);
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
