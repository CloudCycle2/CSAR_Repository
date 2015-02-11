package org.opentosca.csarrepo.service;

import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.DeploymentException;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.model.repository.OpenToscaServerRepository;
import org.opentosca.csarrepo.util.ContainerApiClient;

public class DeployToOpenToscaService extends AbstractService {

	private boolean success = false;
	private static final Logger LOGGER = LogManager.getLogger(DeployToOpenToscaService.class);

	public DeployToOpenToscaService(long userId, long openToscaId, long csarFileId) {
		super(userId);

		OpenToscaServerRepository openToscaServerRepository = new OpenToscaServerRepository();
		CsarFileRepository csarFileRepo = new CsarFileRepository();

		OpenToscaServer openToscaServer;
		CsarFile csarFile;
		try {
			openToscaServer = openToscaServerRepository.getbyId(openToscaId);
			csarFile = csarFileRepo.getbyId(csarFileId);
		} catch (PersistenceException e) {
			this.addError("Couldn't determine required parameters " + e.getMessage());
			return;
		}

		try {
			ContainerApiClient containerApiClient = new ContainerApiClient(openToscaServer);
			String location = containerApiClient.uploadCSAR(csarFile);

			// create meta-data
			csarFile.addOpenToscaServer(openToscaServer, location);
			csarFileRepo.save(csarFile);
			success = true;
		} catch (DeploymentException | PersistenceException | URISyntaxException e) {
			this.addError(e.getMessage());
			return;
		}
	}

	public boolean getResult() {
		this.logInvalidResultAccess("getResult");

		return this.success;
	}

}
