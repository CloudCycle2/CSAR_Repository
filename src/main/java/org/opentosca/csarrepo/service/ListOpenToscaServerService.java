package org.opentosca.csarrepo.service;

import java.util.List;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.repository.OpenToscaServerRepository;

/**
 * Service which returns a List of OpenToscaServers for a specific user
 * 
 * @author Marcus Eisele
 */
public class ListOpenToscaServerService extends AbstractService {

	private final OpenToscaServerRepository openToscaServerRepository = new OpenToscaServerRepository();
	private List<OpenToscaServer> openToscaServers = null;

	/**
	 * @param userId
	 */
	public ListOpenToscaServerService(long userId) {
		super(userId);
		try {
			// TODO: use userId instead of .getAll()
			this.openToscaServers = openToscaServerRepository.getAll();
		} catch (PersistenceException e) {
			AbstractServlet.addError(e.getMessage());
		}
	}

	/**
	 * @return List of OpenToscaServers
	 */
	public List<OpenToscaServer> getResult() {
		super.logInvalidResultAccess("getResult");

		return this.openToscaServers;
	}

}
