package org.opentosca.csarrepo.service;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.filesystem.FileSystem;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.util.DownloadCsarFileObject;

/**
 * Provides download functionality for CSAR files
 * 
 * @author Thomas Kosch, Fabian Toth, Dennis Przytarski
 *
 */
public class DownloadCsarFileService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(DownloadCsarFileService.class);

	private DownloadCsarFileObject downloadCsarFileObject;

	/**
	 * Constructor for the DownloadCsarService
	 *
	 * @param userId
	 *            of the user
	 * @param csarFileId
	 *            id of csar file
	 */
	public DownloadCsarFileService(long userId, long csarFileId) {
		super(userId);
		LOGGER.info("Download for csarFileId {} requested", csarFileId);
		getCsarFile(csarFileId);
	}

	/**
	 * Gets the file from given csar file id
	 * 
	 * @param csarFileId
	 *            id of the csar file
	 */
	private void getCsarFile(long csarFileId) {
		try {
			CsarFileRepository csarFileRepository = new CsarFileRepository();
			CsarFile csarFile = csarFileRepository.getbyId(csarFileId);
			FileSystem fileSystem = new FileSystem();

			File file = fileSystem.getFile(csarFile.getHashedFile().getFilename());
			String filename = csarFile.getName();
			this.downloadCsarFileObject = new DownloadCsarFileObject(file, filename);
		} catch (PersistenceException e) {
			AbstractServlet.addError(e.getMessage());
		}
	}

	/**
	 * 
	 * @return File object which holds the CSAR
	 */
	public DownloadCsarFileObject getResult() {
		super.logInvalidResultAccess("getResult");

		return this.downloadCsarFileObject;
	}
}
