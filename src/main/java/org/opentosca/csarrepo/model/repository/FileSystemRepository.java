package org.opentosca.csarrepo.model.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.opentosca.csarrepo.model.FileSystem;

/**
 * Class to avoid direct access of the hibernate active records for FileSystem
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
	public FileSystem getbyId(long id) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		FileSystem fileSystem = null;
		try {
			tx = session.beginTransaction();
			fileSystem = (FileSystem) session.get(FileSystem.class, id);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new Exception(e);
		} finally {
			session.close();
		}
		return fileSystem;
	}

	/**
	 * Gets all FileSystems.
	 * 
	 * @return List of FileSystem.
	 */
	public List<FileSystem> getAll() throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		List<FileSystem> fileSystemList = null;
		try {
			tx = session.beginTransaction();
			// TODO: use another query method with .class instead of String
			fileSystemList = session.createQuery("from FileSystem").list();
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
	 * @param FileSystem
	 *            to be stored
	 * @return id of the saved FileSystem
	 * @throws Exception
	 *             upon problems commiting the underlying transaction
	 */
	public long save(FileSystem fileSystem) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(fileSystem);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new Exception(e);
		} finally {
			session.close();
		}
		return fileSystem.getId();
	}

	/**
	 * @param fileSystem
	 * @throws Exception
	 *             upon problems committing the underlying transaction
	 */
	public void delete(FileSystem fileSystem) throws Exception {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(fileSystem);
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
	 * Checks if a FileSystem with the given hash exists
	 * 
	 * @param hash
	 * @return true if hash exists
	 */
	public boolean containsHash(String hash) {
		Session session = HibernateUtil.getSession();
		Criteria fileSystemEntryCriteria = session.createCriteria(FileSystem.class);
		fileSystemEntryCriteria.add(Restrictions.eq("hash", hash));
		return (fileSystemEntryCriteria.uniqueResult() != null);
	}

	/**
	 * Returns the FileSystem with the given hash
	 * 
	 * @param hash
	 * @return FileSystem or <code>null</code> if non existent
	 */
	public FileSystem getByHash(String hash) {
		Session session = HibernateUtil.getSession();
		Criteria fileSystemEntryCriteria = session.createCriteria(FileSystem.class);
		fileSystemEntryCriteria.add(Restrictions.eq("hash", hash));
		FileSystem fileSystem = (FileSystem) fileSystemEntryCriteria.uniqueResult();
		session.close();
		return fileSystem;
	}
}
