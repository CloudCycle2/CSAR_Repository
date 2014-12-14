package org.opentosca.csarrepo.service;

import java.io.File;
import java.util.UUID;

import org.opentosca.csarrepo.filesystem.FileSystem;

/**
 * Provides download functionality for CSARs
 * 
 * @author Thomas Kosch, Fabian Toth
 *
 */
public class DownloadCsarService extends AbstractService {

	private File csarFile = null;

	/**
	 * Constructor for the DownloadCsarService
	 *
	 * @param userId
	 *            of the user
	 * @param csarId
	 *            of the CSAR
	 */
	public DownloadCsarService(long userId, UUID csarId) {
		super(userId);
		getCsarFile(csarId);
	}

	/**
	 * Gets the file with the appropriate csarId
	 * 
	 * @param csarId
	 *            of the CSAR to get
	 */
	private void getCsarFile(UUID csarId) {
		FileSystem fs = new FileSystem();
		File file = fs.getFile(csarId);
		if (file != null) {
			this.csarFile = file;
		} else {
			this.addError("Could not find file");
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
