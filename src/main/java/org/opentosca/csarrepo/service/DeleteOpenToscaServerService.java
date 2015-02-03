package org.opentosca.csarrepo.service;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.repository.OpenToscaServerRepository;

/**
 * Service deleting OpenToscaServers
 * 
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
public class DeleteOpenToscaServerService extends AbstractService {

	/**
	 * @param userId
	 * @param opentoscaServerId
	 */
	public DeleteOpenToscaServerService(long userId, long opentoscaServerId) {
		super(userId);

		try {
			OpenToscaServerRepository otServerRepo = new OpenToscaServerRepository();
			OpenToscaServer otServer = otServerRepo.getbyId(opentoscaServerId);

			if (otServer == null) {
				AbstractServlet.addError("invalidOpenToscaServer");
				return;
			}

			otServerRepo.delete(otServer);
		} catch (PersistenceException e) {
			AbstractServlet.addError(e.getMessage());
		}
	}

	/**
	 * @return status of deletion
	 */
	public boolean getResult() {
		super.logInvalidResultAccess("getResult");

		return !super.hasErrors();
	}

}
