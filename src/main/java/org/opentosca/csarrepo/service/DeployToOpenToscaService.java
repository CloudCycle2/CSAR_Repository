package org.opentosca.csarrepo.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import net.lingala.zip4j.exception.ZipException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.DeploymentException;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.filesystem.FileSystem;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.model.repository.OpenToscaServerRepository;
import org.opentosca.csarrepo.util.ContainerApiClient;
import org.opentosca.csarrepo.util.ZipUtils;

public class DeployToOpenToscaService extends AbstractService {

	private boolean success = false;
	private static final Logger LOGGER = LogManager.getLogger(DeployToOpenToscaService.class);

	public DeployToOpenToscaService(long userId, long openToscaId, long csarFileId) throws ZipException, IOException {
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

		// TODO: move this addition of meta data to a utility class, copied from
		// DownloadCsarFileServlet
		FileSystem fileSystem = new FileSystem();
		File file = fileSystem.getFile(csarFile.getHashedFile().getFilename());
		File zipArchive = DownloadCsarFileService.duplicateFile(file);
		File csarRepositoryFile = DownloadCsarFileService.transformBytesToFile(Long.toString(csarFileId).getBytes());

		ZipUtils.add(zipArchive, csarRepositoryFile);
		String filename = csarFile.getName();

		try {
			ContainerApiClient containerApiClient = new ContainerApiClient(openToscaServer);
			String location = containerApiClient.uploadFileToOpenTOSCA(zipArchive, filename);

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
