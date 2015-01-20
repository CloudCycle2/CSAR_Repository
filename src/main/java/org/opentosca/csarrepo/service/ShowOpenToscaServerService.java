package org.opentosca.csarrepo.service;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.repository.OpenToscaServerRepository;

/**
 * Service to show a specific OpenToscaServer
 * 
 * @author eiselems (marcus.eisele@gmail.com)
 */
public class ShowOpenToscaServerService extends AbstractService {

	private final OpenToscaServerRepository openToscaServerRepository = new OpenToscaServerRepository();
	private OpenToscaServer openToscaServer = null;

	/**
	 * @param userId
	 * @param serverId
	 */
	public ShowOpenToscaServerService(long userId, long serverId) {
		super(userId);
		try {
			// TODO: error handling
			// TODO: use userId in addition to serverId
			openToscaServer = openToscaServerRepository.getbyId(serverId);
		} catch (PersistenceException e) {
			this.addError(e.getMessage());
		}

		if (this.openToscaServer == null) {
			this.addError("invalidOpenToscaServer");
		}
	}

	/**
	 * @return List of OpenToscaServer
	 */
	public OpenToscaServer getResult() {
		super.logInvalidResultAccess("getResult");
		return this.openToscaServer;
	}
}
