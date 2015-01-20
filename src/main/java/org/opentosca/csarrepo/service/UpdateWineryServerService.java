package org.opentosca.csarrepo.service;

import java.net.MalformedURLException;
import java.net.URL;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.WineryServer;
import org.opentosca.csarrepo.model.repository.WineryServerRepository;

public class UpdateWineryServerService extends AbstractService {

	/**
	 * @param userId
	 */
	public UpdateWineryServerService(long userId, long wineryServerId,
			String name, String address) {
		super(userId);

		WineryServerRepository repo = new WineryServerRepository();
		
		// validate the name
		name.trim();
		if (name.isEmpty() || name.length() > 255) {
			this.addError("nameLengthError");
		}

		// validate address
		try {
			URL url = new URL(address);
			if (!super.hasErrors()) {
				// no validation errors
				WineryServer ws;
				try {
					// load the wineryServer
					ws = repo.getbyId(wineryServerId);
				} catch (PersistenceException e1) {
					// exception while loading the wineryServer
					this.addError("failed to load WineryServer");
					return;
				}

				if (ws == null) {
					// wineryServer with id does not exist - error
					this.addError("invalidWineryServerError");
					return;
				}

				// update data
				ws.setName(name);
				ws.setAddress(url);
				
				try {
					// save data
					repo.save(ws);
				} catch (PersistenceException e) {
					this.addError("savingWineryFailed");
				}
			}
		} catch (MalformedURLException e1) {
			this.addError("invalidURIError");
		}
	}

}
