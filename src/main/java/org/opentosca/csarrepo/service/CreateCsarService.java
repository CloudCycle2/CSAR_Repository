package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.repository.CsarRepository;

/**
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
public class CreateCsarService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(CreateCsarService.class);

	/**
	 * @param userId
	 * @param file
	 */
	public CreateCsarService(long userId, String name) {
		super(userId);

		CsarRepository csarRepo = new CsarRepository();
		try {
			Csar csar = new Csar();
			csar.setName(name);
			csarRepo.save(csar);
		} catch (Exception e) {
			this.addError(e.getMessage());
			LOGGER.error(e);
		}
	}

	public boolean getResult() {
		super.logInvalidResultAccess("getResult");

		return !this.hasErrors();
	}

}
