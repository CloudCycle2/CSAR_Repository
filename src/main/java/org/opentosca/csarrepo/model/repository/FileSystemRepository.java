package org.opentosca.csarrepo.model.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.opentosca.csarrepo.model.HashedFile;

/**
 * Class to avoid direct access of the hibernate active records for HashedFile
 * 
 * @author Marcus Eisele, Dennis Przytarski
 *
 */
public class FileSystemRepository {

	/**
	 * Returns a fileSystem for the given id.
	 * 
	 * @param id
	 * @return fileSystem
	 */
	public HashedFile getbyId(long id) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		HashedFile hashedFile = null;
		try {
			tx = session.beginTransaction();
			hashedFile = (HashedFile) session.get(HashedFile.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new Exception(e);
		} finally {
			session.close();
		}
		return hashedFile;
	}

	/**
	 * Gets all FileSystems.
	 * 
	 * @return List of HashedFile.
	 */
	public List<HashedFile> getAll() throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		List<HashedFile> fileSystemList = null;
		try {
			tx = session.beginTransaction();
			// TODO: use another query method with .class instead of String
			fileSystemList = session.createQuery("from HashedFile").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new Exception(e);
		} finally {
			session.close();
		}
		return fileSystemList;
	}

	/**
	 * @param HashedFile
	 *            to be stored
	 * @return id of the saved HashedFile
	 * @throws Exception
	 *             upon problems commiting the underlying transaction
	 */
	public long save(HashedFile hashedFile) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(hashedFile);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new Exception(e);
		} finally {
			session.close();
		}
		return hashedFile.getId();
	}

	/**
	 * @param hashedFile
	 * @throws Exception
	 *             upon problems committing the underlying transaction
	 */
	public void delete(HashedFile hashedFile) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(hashedFile);
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

	/**
	 * Checks if a HashedFile with the given hash exists
	 * 
	 * @param hash
	 * @return true if hash exists
	 */
	public boolean containsHash(String hash) {
		Session session = HibernateUtil.getSession();
		Criteria fileSystemEntryCriteria = session.createCriteria(HashedFile.class);
		fileSystemEntryCriteria.add(Restrictions.eq("hash", hash));
		return (fileSystemEntryCriteria.uniqueResult() != null);
	}

	/**
	 * Returns the HashedFile with the given hash
	 * 
	 * @param hash
	 * @return HashedFile or <code>null</code> if non existent
	 */
	public HashedFile getByHash(String hash) {
		Session session = HibernateUtil.getSession();
		Criteria fileSystemEntryCriteria = session.createCriteria(HashedFile.class);
		fileSystemEntryCriteria.add(Restrictions.eq("hash", hash));
		HashedFile hashedFile = (HashedFile) fileSystemEntryCriteria.uniqueResult();
		session.close();
		return hashedFile;
	}
}
