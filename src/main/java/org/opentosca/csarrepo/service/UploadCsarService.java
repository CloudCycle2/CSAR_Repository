package org.opentosca.csarrepo.service;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

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
public class UploadCsarService extends AbstractService {

	/**
	 * @param userId
	 * @param file
	 */
	public UploadCsarService(long userId, UUID csarId, InputStream is, String name) {
		super(userId);

		storeFile(csarId, is, name);
	}

	/**
	 * @param userId
	 * @param file
	 */
	public UploadCsarService(long userId, InputStream is, String name) {
		super(userId);

		storeFile(UUID.randomUUID(), is, name);
	}

	private void storeFile(UUID csarId, InputStream is, String name) {
		FileSystemRepository fileSystemRepository = new FileSystemRepository();
		CsarRepository csarRepository = new CsarRepository();
		CsarFileRepository csarFileRepository = new CsarFileRepository();

		try {
			FileSystem fs = new FileSystem();
			File tmpFile = fs.saveTempFile(is);
			String hash = fs.generateHash(tmpFile);
			HashedFile hashedFile;

			if (!fileSystemRepository.containsHash(hash)) {
				hashedFile = new HashedFile();
				String fileName = fs.saveToFileSystem(csarId, tmpFile);
				hashedFile.setHash(hash);
				hashedFile.setFileName(fileName);
				fileSystemRepository.save(hashedFile);
			} else {
				hashedFile = fileSystemRepository.getByHash(hash);
			}

			CsarFile csarFile = new CsarFile();
			csarFile.setHashedFile(hashedFile);
			csarFile.setSize(fs.getFileSize(csarId));
			csarFile.setVersion("1.0");
			csarFile.setFileId(csarId);
			// TODO: set Date correctly
			// check if file.lastModified() uses same long as Date(long)
			csarFile.setUploadDate(new Date());

			Csar csar = null;
			for (Csar cs : csarRepository.getAll()) {
				if (cs.getName().equals(name)) {
					csar = cs;
					break;
				}
			}
			if (csar == null) {
				csar = new Csar();
				csar.setName(name);
			}
			csar.getCsarFiles().add(csarFile);
			csarFile.setCsar(csar);
			csarRepository.save(csar);
			csarFileRepository.save(csarFile);

		} catch (Exception e) {
			this.addError(e.getMessage());
		}
	}

	public boolean getResult() {
		return !this.hasErrors();
	}

}
