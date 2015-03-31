package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.repository.WineryServerRepository;

public class CountWineryServerService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(CountWineryServerService.class);
	long count;

	/**
	 * Counts the WineryServers in database
	 */
	public CountWineryServerService(long userId) {
		super(userId);

		try {
			WineryServerRepository repo = new WineryServerRepository();
			this.count = repo.count();
		} catch (PersistenceException e) {
			LOGGER.error(e);
			super.addError("Loading WineryServer count failed");
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
