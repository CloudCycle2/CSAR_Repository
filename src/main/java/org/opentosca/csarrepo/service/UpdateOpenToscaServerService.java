package org.opentosca.csarrepo.service;

import java.net.MalformedURLException;
import java.net.URL;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.repository.OpenToscaServerRepository;

public class UpdateOpenToscaServerService extends AbstractService {

	/**
	 * @param userId
	 */
	public UpdateOpenToscaServerService(long userId, long openToscaServerId, String name, String address) {
		super(userId);

		OpenToscaServerRepository repo = new OpenToscaServerRepository();

		// validate the name
		name.trim();
		if (name.isEmpty() || name.length() > 255) {
			AbstractServlet.addError("nameLengthError");
		}

		// validate address
		try {
			URL url = new URL(address);
			if (!super.hasErrors()) {
				// no validation errors
				OpenToscaServer ots;
				try {
					// load the OpenToscaServer
					ots = repo.getbyId(openToscaServerId);
				} catch (PersistenceException e1) {
					// exception while loading the OpenToscaServer
					AbstractServlet.addError("failed to load OpenToscaServer");
					return;
				}

				if (ots == null) {
					// OpenToscaServer with id does not exist - error
					AbstractServlet.addError("invalidOpenToscaServerError");
					return;
				}

				// update data
				ots.setName(name);
				ots.setAddress(url);

				try {
					// save data
					repo.save(ots);
				} catch (PersistenceException e) {
					AbstractServlet.addError("savingOpenToscaServerFailed");
				}
			}
		} catch (MalformedURLException e1) {
			AbstractServlet.addError("invalidURIError");
		}
	}

}
