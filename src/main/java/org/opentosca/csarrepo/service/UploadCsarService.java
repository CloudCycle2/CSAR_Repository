package org.opentosca.csarrepo.service;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import org.opentosca.csarrepo.filesystem.FileSystem;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.model.repository.CsarRepository;

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
		CsarFileRepository csarFileRepository = new CsarFileRepository();
		CsarRepository csarRepository = new CsarRepository();

		try {

			FileSystem fs = new FileSystem();
			if (!fs.saveToFileSystem(csarId, is)) {
				this.addError("Could not save the file");
			}

			CsarFile csarFile = new CsarFile();
			csarFile.setHash("12345");
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
