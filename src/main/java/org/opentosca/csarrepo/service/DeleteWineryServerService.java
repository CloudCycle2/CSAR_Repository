package org.opentosca.csarrepo.service;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.WineryServer;
import org.opentosca.csarrepo.model.repository.WineryServerRepository;

public class DeleteWineryServerService extends AbstractService {

	/**
	 * @param userId
	 * @param wineryServerId
	 */
	public DeleteWineryServerService(long userId, long wineryId) {
		super(userId);

		try {
			WineryServerRepository wineryServerRepo = new WineryServerRepository();
			WineryServer wineryServer = wineryServerRepo.getbyId(wineryId);
			
			if(wineryServer == null) {
				AbstractServlet.addError("invalidWineryServer");
				return;
			}
			
			wineryServerRepo.delete(wineryServer);
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