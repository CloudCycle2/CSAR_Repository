package org.opentosca.csarrepo.service;

import java.util.UUID;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.filesystem.FileSystem;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.HashedFile;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.model.repository.FileSystemRepository;

/**
 * Deletes the given CSAR file.
 * 
 * @author Dennis Przytarski
 */
public class DeleteCsarFileService extends AbstractService {

	private long csarFileId;
	private boolean returnValue = false;
	private Csar csar;

	/**
	 * @param userId
	 * @param csarFileId
	 */
	public DeleteCsarFileService(long userId, long csarFileId) {
		super(userId);
		this.csarFileId = csarFileId;

		try {
			FileSystemRepository fileSystemRepository = new FileSystemRepository();
			CsarFileRepository csarFileRepository = new CsarFileRepository();
			CsarFile csarFile = csarFileRepository.getbyId(this.csarFileId);
			this.csar = csarFile.getCsar();

			HashedFile hashedFile = csarFile.getHashedFile();

			// csar file must be deleted before hashed file
			csarFileRepository.delete(csarFile);

			// delete hashed file from database and file system, if necessary
			if (fileSystemRepository.isHashDeletable(hashedFile.getHash())) {
				fileSystemRepository.delete(hashedFile);
				FileSystem fileSystem = new FileSystem();
				fileSystem.deleteFromFileSystem(UUID.fromString(hashedFile.getFileName()));
			}

			this.returnValue = true;
		} catch (PersistenceException e) {
			this.addError(e.getMessage());
		}
	}

	/**
	 * @return status of deletion
	 */
	public boolean getResult() {
		super.logInvalidResultAccess("getResult");

		return this.returnValue;
	}

	/**
	 * 
	 * @return a Csar
	 */
	public Csar getCsar() {
		super.logInvalidResultAccess("getCsar");
		return this.csar;
	}

}
