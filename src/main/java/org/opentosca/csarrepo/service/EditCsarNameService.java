package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.repository.CsarRepository;

/**
 * Implementation of a service which handles renaming existing
 *
 * @author Thomas Kosch (mail@thomaskosch.com)
 *
 */
public class EditCsarNameService extends AbstractService {

	long csarId = -1;
	private static final Logger LOGGER = LogManager.getLogger(EditCsarNameService.class);

	public EditCsarNameService(long userId, long csarId, String updatedName) {
		super(userId);

		try {
			CsarRepository csarRepository = new CsarRepository();
			Csar csar = csarRepository.getbyId(csarId);
			if (0 == updatedName.length()) {
				throw new PersistenceException("No empty name allowed.");
			}
			if (null == csar) {
				throw new NullPointerException("CSAR is null.");
			}
			csar.setName(updatedName);
			csarRepository.save(csar);
			csarId = csar.getId();

		} catch (PersistenceException e) {
			this.addError(e.getMessage());
			LOGGER.error(e);
		}
	}

	public long getResult() {
		super.logInvalidResultAccess("getResult");

		return csarId;
	}

}
