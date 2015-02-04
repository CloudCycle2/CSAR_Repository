package org.opentosca.csarrepo.service;

import java.util.List;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.WineryServer;
import org.opentosca.csarrepo.model.repository.WineryServerRepository;

public class ListWineryServerService extends AbstractService {

	private final WineryServerRepository wineryServerRepo = new WineryServerRepository();
	private List<WineryServer> wineryServers = null;

	/**
	 * @param userId
	 */
	public ListWineryServerService(long userId) {
		super(userId);
		try {
			// TODO handle user id
			this.wineryServers = wineryServerRepo.getAll();
		} catch (PersistenceException e) {
			this.addError(e.getMessage());
		}
	}

	/**
	 * @return List of WineryServers
	 */
	public List<WineryServer> getResult() {
		super.logInvalidResultAccess("getResult");

		return this.wineryServers;
	}

}
