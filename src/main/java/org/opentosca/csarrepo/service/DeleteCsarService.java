package org.opentosca.csarrepo.service;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.model.repository.CsarRepository;

/**
 * @author Dennis Przytarski
 */
public class DeleteCsarService extends AbstractService {

	private long csarId;
	private boolean returnValue = false;

	/**
	 * @param userId
	 * @param csarId
	 */
	public DeleteCsarService(long userId, long csarId) {
		super(userId);
		this.csarId = csarId;

		try {
			CsarRepository csarRepository = new CsarRepository();
			CsarFileRepository csarFileRepository = new CsarFileRepository();
			Csar csar = csarRepository.getbyId(this.csarId);
			
			if(csar == null) {
				this.addError("invalidCsar");
				return;
			}
			
			for (CsarFile csarFile : csar.getCsarFiles()) {
				csarFileRepository.delete(csarFile);
			}
			csarRepository.delete(csar);
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

}
