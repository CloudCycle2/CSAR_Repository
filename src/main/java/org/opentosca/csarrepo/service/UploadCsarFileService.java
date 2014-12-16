package org.opentosca.csarrepo.service;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	/**
	 * @param userId
	 * @param file
	 */
	public UploadCsarFileService(long userId, long csarId, InputStream is, String name) {
		super(userId);

		storeFile(csarId, is, name);
	}

	private void storeFile(long csarId, InputStream is, String name) {

		FileSystemRepository fileSystemRepository = new FileSystemRepository();
		CsarRepository csarRepository = new CsarRepository();
		CsarFileRepository csarFileRepository = new CsarFileRepository();

		try {
			Csar csar = csarRepository.getbyId(csarId);
			if (csar == null) {
				String errorMsg = String.format("CSAR with ID: %d couldn't be found", csarId);
				this.addError(errorMsg);
				LOGGER.error(errorMsg);
				return;
			}

			FileSystem fs = new FileSystem();
			File tmpFile = fs.saveTempFile(is);
			String hash = fs.generateHash(tmpFile);
			HashedFile hashedFile;

			if (!fileSystemRepository.containsHash(hash)) {
				UUID randomFileName = UUID.randomUUID();

				hashedFile = new HashedFile();
				String fileName = fs.saveToFileSystem(randomFileName, tmpFile);
				hashedFile.setHash(hash);
				hashedFile.setFileName(fileName);
				// TODO fileSize as String has to be calculated correctly
				hashedFile.setSize("200");
				fileSystemRepository.save(hashedFile);
			} else {
				hashedFile = fileSystemRepository.getByHash(hash);
			}

			CsarFile csarFile = new CsarFile();
			csarFile.setHashedFile(hashedFile);
			csarFile.setVersion("1.0");
			// TODO: set Date correctly
			// check if file.lastModified() uses same long as Date(long)
			csarFile.setUploadDate(new Date());
			csarFile.setCsar(csar);
			csarFile.setName(name);
			csarFileRepository.save(csarFile);

			csar.getCsarFiles().add(csarFile);
			csarRepository.save(csar);

		} catch (Exception e) {
			this.addError(e.getMessage());
			LOGGER.error(e.getMessage());
			return;
		}
	}

	public boolean getResult() {
		super.logInvalidResultAccess("getResult");

		return !this.hasErrors();
	}

}
