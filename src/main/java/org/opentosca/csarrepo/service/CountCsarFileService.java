package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;

public class CountCsarFileService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(CountCsarFileService.class);
	long count;

	/**
	 * Counts the CsarFiles in database
	 */
	public CountCsarFileService(long userId) {
		super(userId);

		try {
			CsarFileRepository repo = new CsarFileRepository();
			this.count = repo.count();
		} catch (PersistenceException e) {
			LOGGER.error(e);
			super.addError("Loading CsarFile count failed");
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
