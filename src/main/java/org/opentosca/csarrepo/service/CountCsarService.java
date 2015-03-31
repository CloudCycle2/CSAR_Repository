package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.repository.CsarRepository;

public class CountCsarService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(CountCsarService.class);
	long count;

	/**
	 * Counts the Csars in database
	 */
	public CountCsarService(long userId) {
		super(userId);

		try {
			CsarRepository repo = new CsarRepository();
			this.count = repo.count();
		} catch (PersistenceException e) {
			LOGGER.error(e);
			super.addError("Loading CSAR count failed");
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
