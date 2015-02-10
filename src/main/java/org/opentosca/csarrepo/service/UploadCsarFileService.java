package org.opentosca.csarrepo.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.filesystem.FileSystem;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.HashedFile;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.model.repository.CsarRepository;
import org.opentosca.csarrepo.model.repository.FileSystemRepository;
import org.opentosca.csarrepo.util.Extractor;

/**
 * @author eiselems (marcus.eisele@gmail.com), Dennis Przytarski
 *
 */
public class UploadCsarFileService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(UploadCsarFileService.class);

	private CsarFile csarFile;

	/**
	 * @param userId
	 * @param file
	 * @throws IOException
	 */
	public UploadCsarFileService(long userId, long csarId, InputStream inputStream, String name) {
		super(userId);

		if (!checkExtension(name, "csar")) {
			this.addError(String.format("Uploaded file %s does not contain required extension", name));
			return;
		}

		storeFile(csarId, inputStream, name);
	}

	/**
	 * Checks, if the name contains the given extension.
	 * 
	 * @param name
	 * @param extension
	 * @return true, if given name contains given extension
	 */
	private boolean checkExtension(String name, String extension) {
		int index = name.lastIndexOf('.');
		return 0 < index && name.substring(index + 1).equals(extension);
	}

	/**
	 * Moves the uploaded file to the filesystem and creates a csar file.
	 * 
	 * @param csarId
	 * @param inputStream
	 * @param name
	 */
	private void storeFile(long csarId, InputStream inputStream, String name) {
		CsarRepository csarRepository = new CsarRepository();
		CsarFileRepository csarFileRepository = new CsarFileRepository();
		FileSystemRepository fileSystemRepository = new FileSystemRepository();

		try {
			Csar csar = csarRepository.getbyId(csarId);
			if (null == csar) {
				String errorMsg = String.format("CSAR with ID: %d could not be found", csarId);
				this.addError(errorMsg);
				LOGGER.error(errorMsg);
				return;
			}

			FileSystem fileSystem = new FileSystem();
			File temporaryFile = fileSystem.saveTempFile(inputStream);

			try {
				String entryDefinition = Extractor.extract(temporaryFile, "TOSCA-Metadata/TOSCA.meta",
						"Entry-Definitions: ([\\S]+)\\n");
			} catch (IllegalStateException | IOException e) {
				String errorMsg = String.format("TOSCA.meta file in csar archive %s not found.",
						temporaryFile.getName());
				this.addError(errorMsg);
				LOGGER.error(errorMsg);
				return;
			}

			String hash = fileSystem.generateHash(temporaryFile);
			HashedFile hashedFile;

			if (!fileSystemRepository.containsHash(hash)) {
				hashedFile = new HashedFile();
				File newFile = fileSystem.saveToFileSystem(temporaryFile);
				hashedFile.setFilename(UUID.fromString(newFile.getName()));
				hashedFile.setHash(hash);
				hashedFile.setSize(newFile.length());
				fileSystemRepository.save(hashedFile);
			} else {
				hashedFile = fileSystemRepository.getByHash(hash);
			}

			this.csarFile = new CsarFile();
			this.csarFile.setCsar(csar);
			this.csarFile.setHashedFile(hashedFile);
			this.csarFile.setName(name);
			this.csarFile.setUploadDate(new Date());
			this.csarFile.setVersion(1); // TODO: set correct version
			csarFileRepository.save(csarFile);

			csar.getCsarFiles().add(csarFile);
			csarRepository.save(csar);
		} catch (PersistenceException e) {
			this.addError(e.getMessage());
			LOGGER.error(e.getMessage());
			return;
		}
	}

	public CsarFile getResult() {
		super.logInvalidResultAccess("getResult");

		return this.csarFile;
	}

}
