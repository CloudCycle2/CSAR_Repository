package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.repository.CsarPlanRepository;

public class CountCsarPlanService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(CountCsarPlanService.class);
	long count;

	/**
	 * Counts the Plans in database
	 */
	public CountCsarPlanService(long userId) {
		super(userId);

		try {
			CsarPlanRepository repo = new CsarPlanRepository();
			this.count = repo.count();
		} catch (PersistenceException e) {
			LOGGER.error(e);
			super.addError("Loading CsarPlan count failed");
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
