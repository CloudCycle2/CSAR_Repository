package org.opentosca.csarrepo.service;

import java.net.MalformedURLException;
import java.net.URL;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.repository.OpenToscaServerRepository;
import org.opentosca.csarrepo.model.repository.UserRepository;

public class CreateOpenToscaServerService extends AbstractService {

	public CreateOpenToscaServerService(long userId, String name, String uri) {
		super(userId);

		// validate the name
		name.trim();
		if (name.isEmpty() || name.length() > 255) {
			this.addError("nameLengthError");
		}

		// validate uri
		try {
			URL address = new URL(uri);
			if (!super.hasErrors()) {
				// no errors save
				OpenToscaServer ots = new OpenToscaServer();
				ots.setName(name);
				ots.setAddress(address);
				OpenToscaServerRepository repo = new OpenToscaServerRepository();
				try {
					UserRepository usrRepo = new UserRepository();
					ots.addUser(usrRepo.getById(userId));
					repo.save(ots);
				} catch (PersistenceException e) {
					this.addError("savingWineryFailed" + e.getMessage());
				}
			}
		} catch (MalformedURLException e1) {
			this.addError("invalidURIError");
		}
	}

	public boolean getResult() {
		return !super.hasErrors();
	}
}
