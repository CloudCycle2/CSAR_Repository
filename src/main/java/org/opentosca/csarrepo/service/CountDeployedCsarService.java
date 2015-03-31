package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.repository.JoinRepository;

public class CountDeployedCsarService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(CountDeployedCsarService.class);
	long count;

	/**
	 * Counts the deployed Csars in database
	 */
	public CountDeployedCsarService(long userId) {
		super(userId);

		try {
			JoinRepository repo = new JoinRepository();
			this.count = repo.count();
		} catch (PersistenceException e) {
			LOGGER.error(e);
			super.addError("Loading deployed CSAR count failed");
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
