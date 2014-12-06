package org.opentosca.csarrepo.service;

import java.io.File;
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

	private long csarFileId;
	private long csarId;

	/**
	 * @param userId
	 * @param file
	 */
	public UploadCsarService(long userId, UUID csarID, InputStream is, String name) {
		super(userId);

		storeFile(csarID, is, name);

	}

	/**
	 * @param userId
	 * @param file
	 */
	public UploadCsarService(long userId, InputStream is, String name) {
		super(userId);

		storeFile(UUID.randomUUID(), is, name);
	}

	private void storeFile(UUID csarID, InputStream is, String name) {
		File file = null;
		CsarFileRepository csarFileRepository = new CsarFileRepository();
		CsarRepository csarRepository = new CsarRepository();

		try {

			FileSystem fs = new FileSystem();
			file = fs.saveTempFile(is);
			Long size = file.length();
			String absPath = fs.saveToFileSystem(file);

			CsarFile csarFile = new CsarFile();
			csarFile.setHash("12345");
			csarFile.setSize(size);
			csarFile.setVersion("1.0");
			// TODO: set Date correctly
			// check if file.lastModified() uses same long as Date(long)
			csarFile.setUploadDate(new Date());
			csarFile.setPath(absPath);

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
			csarId = csarRepository.save(csar);
			csarFileId = csarFileRepository.save(csarFile);

		} catch (Exception e) {
			this.addError(e.getMessage());
		}
	}

	public long getResult() {
		return csarFileId;
	}

}
