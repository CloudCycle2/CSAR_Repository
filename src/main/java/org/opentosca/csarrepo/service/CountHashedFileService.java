package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.repository.FileSystemRepository;

public class CountHashedFileService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(CountHashedFileService.class);
	long count;

	/**
	 * Counts the HashedFiles in database
	 */
	public CountHashedFileService(long userId) {
		super(userId);

		try {
			FileSystemRepository repo = new FileSystemRepository();
			this.count = repo.count();
		} catch (PersistenceException e) {
			LOGGER.error(e);
			super.addError("Loading HashedFile count failed");
		}
	}

	/**
	 * 
	 * @return count
	 */
	public long getResult() {
		super.logInvalidResultAccess("getResult");

		return this.count;
	}
}
