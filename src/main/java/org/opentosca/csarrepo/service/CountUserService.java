package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.repository.UserRepository;

public class CountUserService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(CountUserService.class);
	long count;

	/**
	 * Counts the Users in database
	 */
	public CountUserService(long userId) {
		super(userId);

		try {
			UserRepository repo = new UserRepository();
			this.count = repo.count();
		} catch (PersistenceException e) {
			LOGGER.error(e);
			super.addError("Loading User count failed");
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
