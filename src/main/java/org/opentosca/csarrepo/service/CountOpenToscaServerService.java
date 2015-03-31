package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.repository.OpenToscaServerRepository;

public class CountOpenToscaServerService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(CountOpenToscaServerService.class);
	long count;

	/**
	 * Counts the OpenToscaServers in database
	 */
	public CountOpenToscaServerService(long userId) {
		super(userId);

		try {
			OpenToscaServerRepository repo = new OpenToscaServerRepository();
			this.count = repo.count();
		} catch (PersistenceException e) {
			LOGGER.error(e);
			super.addError("Loading OpenToscaServer count failed");
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
