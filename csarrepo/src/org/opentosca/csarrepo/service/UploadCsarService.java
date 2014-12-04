package org.opentosca.csarrepo.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import org.opentosca.csarrepo.filesystem.FileSystem;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;

/**
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
public class UploadCsarService extends AbstractService {

	private long csarFileId;

	/**
	 * @param userId
	 * @param file
	 */
	public UploadCsarService(long userId, long csarID, FileInputStream fis) {
		// FIXME: is csarID optional (has it to be nullable)
		super(userId);

		storeFile(csarID, fis);

	}

	private void storeFile(Long csarID, FileInputStream fis) {
		// TODO: if csarID is null create new Csar, otherwise add csarFile as
		// new revision to csar
		File file = null;
		CsarFileRepository csarFileRepository = new CsarFileRepository();

		try {
			FileSystem fs = new FileSystem();
			file = fs.saveTempFile(fis);
			String absPath = fs.saveToFileSystem(file);
			CsarFile csarFile = new CsarFile();
			csarFile.setHash("12345");
			csarFile.setSize(file.length());
			//TODO: set Date correctly
			//check if file.lastModified() uses same long as Date(long)
			csarFile.setUploadDate(new Date());
			csarFile.setPath(absPath);
			csarFileId = csarFileRepository.save(csarFile);

		} catch (Exception e) {
			this.addError(e.getMessage());
		}
	}

	public long getResult() {
		return csarFileId;
	}

}
