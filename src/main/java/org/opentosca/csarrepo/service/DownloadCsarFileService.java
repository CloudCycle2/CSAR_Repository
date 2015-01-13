package org.opentosca.csarrepo.service;

import java.io.File;
import java.util.UUID;

import org.opentosca.csarrepo.filesystem.FileSystem;

/**
 * Provides download functionality for CSAR files
 * 
 * @author Thomas Kosch, Fabian Toth, Dennis Przytarski
 *
 */
public class DownloadCsarFileService extends AbstractService {

	private File csarFile = null;

	/**
	 * Constructor for the DownloadCsarService
	 *
	 * @param userId
	 *            of the user
	 * @param filename
	 *            filename (= UUID) of the csar file
	 */
	public DownloadCsarFileService(long userId, UUID filename) {
		super(userId);
		getCsarFile(filename);
	}

	/**
	 * Gets the file with the appropriate csarId
	 * 
	 * @param filename
	 *            filename (= UUID) of the csar file
	 */
	private void getCsarFile(UUID filename) {
		FileSystem fs = new FileSystem();
		File file = fs.getFile(filename);
		if (null != file) {
			this.csarFile = file;
		} else {
			this.addError("Could not find csar file");
		}
	}

	/**
	 * Get CSAR as file object
	 * 
	 * @return File object which holds the CSAR
	 */
	public File getResult() {
		super.logInvalidResultAccess("getResult");

		return this.csarFile;
	}
}
