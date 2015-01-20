package org.opentosca.csarrepo.service;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.WineryServer;
import org.opentosca.csarrepo.model.repository.WineryServerRepository;

/**
 * Service to show a winery server
 */
public class ShowWineryServerService extends AbstractService {

	private final WineryServerRepository wineryServerRepository = new WineryServerRepository();
	private WineryServer wineryServer = null;

	/**
	 * @param userId
	 * @param wineryServerId
	 */
	public ShowWineryServerService(long userId, long wineryServerId) {
		super(userId);
		try {
			this.wineryServer = this.wineryServerRepository.getbyId(wineryServerId);
		} catch (PersistenceException e) {
			this.addError(e.getMessage());
		}

		if (this.wineryServer == null) {
			this.addError("invalidWineryServer");
		}
	}

	/**
	 * @return List of CSARs
	 */
	public WineryServer getResult() {
		super.logInvalidResultAccess("getResult");

		return this.wineryServer;
	}
}
