package org.opentosca.csarrepo.service;

import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;

/**
 * Deletes the given CSAR file.
 * 
 * @author Dennis Przytarski
 */
public class DeleteCsarFileService extends AbstractService {

	private long csarFileId;
	private boolean returnValue = false;

	/**
	 * @param userId
	 * @param csarFileId
	 */
	public DeleteCsarFileService(long userId, long csarFileId) {
		super(userId);
		this.csarFileId = csarFileId;

		try {
			CsarFileRepository csarFileRepository = new CsarFileRepository();
			CsarFile csarFile = csarFileRepository.getbyId(this.csarFileId);
			csarFileRepository.delete(csarFile);
			this.returnValue = true;
		} catch (Exception e) {
			this.addError(e.getMessage());
		}
	}

	/**
	 * @return status of deletion
	 */
	public boolean getResult() {
		return returnValue;
	}

}
