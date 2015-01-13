package org.opentosca.csarrepo.service;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

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

/**
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
public class UploadCsarFileService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(UploadCsarFileService.class);

	private CsarFile csarFile;

	/**
	 * @param userId
	 * @param file
	 */
	public UploadCsarFileService(long userId, long csarId, InputStream inputStream, String name) {
		super(userId);

		storeFile(csarId, inputStream, name);
	}

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

			String hash = fileSystem.generateHash(temporaryFile);
			HashedFile hashedFile;

			if (!fileSystemRepository.containsHash(hash)) {
				hashedFile = new HashedFile();
				File newFile = fileSystem.saveToFileSystem(temporaryFile);
				hashedFile.setFilename(newFile.getName());
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
