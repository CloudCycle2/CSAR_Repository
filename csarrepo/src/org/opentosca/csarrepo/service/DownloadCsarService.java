package org.opentosca.csarrepo.service;

import java.io.File;

import javax.persistence.PersistenceException;

import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;

/**
 * Provides download functionality for CSARs
 * 
 * @author Thomas Kosch (mail@thomaskosch.com)
 *
 */
public class DownloadCsarService extends AbstractService {

	private File csarFileObject = null;

	/**
	 * Constructor for the DownloadCsarService
	 *
	 * @param userId
	 *            of the user
	 * @param csarId
	 *            of the CSAR
	 */
	public DownloadCsarService(long userId, long csarId) {
		super(userId);
		getCsarFile(csarId);
	}

	/**
	 * Gets the file with the appropriate csarId
	 * 
	 * @param csarId
	 *            of the CSAR to get
	 */
	private void getCsarFile(long csarId) {
		CsarFileRepository csarFileRepository = new CsarFileRepository();
		CsarFile csarFile = csarFileRepository.getbyId(csarId);
		csarFileObject = new File(csarFile.getPath());
	}

	/**
	 * Get CSAR as file object
	 * 
	 * @return File object which holds the CSAR
	 */
	public File getResult() {
		try {
			if (csarFileObject.exists() && csarFileObject.isFile()) {
				return csarFileObject;
			}
		} catch (PersistenceException e) {
			this.addError(e.getMessage());
		}
		return null;
	}
}