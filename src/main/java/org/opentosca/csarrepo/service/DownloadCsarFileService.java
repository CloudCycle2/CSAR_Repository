package org.opentosca.csarrepo.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import net.lingala.zip4j.exception.ZipException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.filesystem.FileSystem;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.util.DownloadCsarFileObject;
import org.opentosca.csarrepo.util.ZipUtils;

/**
 * Provides download functionality for CSAR files
 * 
 * @author Thomas Kosch, Fabian Toth, Dennis Przytarski
 *
 */
public class DownloadCsarFileService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(DownloadCsarFileService.class);
	private static final String CSAR_REPOSITORY_FILENAME = "CSAR-REPOSITORY.txt";

	private DownloadCsarFileObject downloadCsarFileObject;

	/**
	 * Constructor for the DownloadCsarService
	 *
	 * @param userId
	 *            of the user
	 * @param csarFileId
	 *            id of csar file
	 */
	public DownloadCsarFileService(long userId, long csarFileId) {
		super(userId);
		LOGGER.info("Download for csarFileId {} requested", csarFileId);
		getCsarFile(csarFileId);
	}

	/**
	 * Gets the file from given csar file id
	 * 
	 * @param csarFileId
	 *            id of the csar file
	 */
	private void getCsarFile(long csarFileId) {
		try {
			CsarFileRepository csarFileRepository = new CsarFileRepository();
			CsarFile csarFile = csarFileRepository.getbyId(csarFileId);
			FileSystem fileSystem = new FileSystem();

			File file = fileSystem.getFile(csarFile.getHashedFile().getFilename());
			File zipArchive = duplicateFile(file);
			File csarRepositoryFile = transformBytesToFile(Long.toString(csarFileId).getBytes());

			ZipUtils.add(zipArchive, csarRepositoryFile);
			String filename = csarFile.getName();

			this.downloadCsarFileObject = new DownloadCsarFileObject(zipArchive, filename);
		} catch (IOException | PersistenceException | ZipException e) {
			this.addError(e.getMessage());
		}
	}

	/**
	 * 
	 * @return File object which holds the CSAR
	 */
	public DownloadCsarFileObject getResult() {
		super.logInvalidResultAccess("getResult");

		return this.downloadCsarFileObject;
	}

	/**
	 * Duplicates a given file
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private File duplicateFile(File file) throws IOException {
		File duplicatedFile = File.createTempFile("abc", "zip");
		duplicatedFile.deleteOnExit();
		Files.copy(file.toPath(), duplicatedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		return duplicatedFile;
	}

	/**
	 * Transforms the given bytes to a file object
	 * 
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	private File transformBytesToFile(byte[] bytes) throws IOException {
		File file = new File(CSAR_REPOSITORY_FILENAME);
		file.deleteOnExit();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		Files.copy(inputStream, Paths.get(file.getPath()), StandardCopyOption.REPLACE_EXISTING);
		return file;
	}

}
