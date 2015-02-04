package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.repository.CsarRepository;

/**
 * @author eiselems (marcus.eisele@gmail.com), Dennis Przytarski
 *
 */
public class CreateCsarService extends AbstractService {

	long csarId = -1;

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
			csarId = csar.getId();
		} catch (PersistenceException e) {
			AbstractServlet.addError(e.getMessage());
			LOGGER.error(e);
		}
	}

	public long getResult() {
		super.logInvalidResultAccess("getResult");

		return csarId;
	}

}
