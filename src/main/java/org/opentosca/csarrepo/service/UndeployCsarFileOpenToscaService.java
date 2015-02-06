package org.opentosca.csarrepo.service;

import java.net.URISyntaxException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.DeploymentException;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.join.CsarFileOpenToscaServer;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.model.repository.JoinRepository;
import org.opentosca.csarrepo.model.repository.OpenToscaServerRepository;
import org.opentosca.csarrepo.util.ContainerApiClient;

public class UndeployCsarFileOpenToscaService extends AbstractService {

	private boolean success = false;
	private static final Logger LOGGER = LogManager.getLogger(UndeployCsarFileOpenToscaService.class);

	public UndeployCsarFileOpenToscaService(long userId, long openToscaId, long csarFileId) {
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
			URL address = openToscaServer.getAddress();
			// TODO: check if it would be better to save address as URI in
			// general
			ContainerApiClient containerApiClient = new ContainerApiClient(address.toURI());
			JoinRepository joinRepo = new JoinRepository();
			CsarFileOpenToscaServer mapping = joinRepo.getCsarFileOpenToscaServer(csarFile, openToscaServer);
			containerApiClient.deleteCsarAtLocation(mapping.getLocation());
			// update meta-data
			csarFile.removeOpenToscaServer(openToscaServer);
			success = true;

		} catch (URISyntaxException | PersistenceException | DeploymentException e) {
			this.addError(e.getMessage());
			return;
		}
	}

	public boolean getResult() {
		this.logInvalidResultAccess("getResult");

		return this.success;
	}

}
