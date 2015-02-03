package org.opentosca.csarrepo.service;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;

public class ShowCsarFileService extends AbstractService {

	private CsarFile csarFile;
	private CsarFileRepository repo = new CsarFileRepository();
	
	/**
	 * 
	 * @param userId
	 * @param csarFileId
	 */
	public ShowCsarFileService(long userId, long csarFileId) {
		super(userId);
		
		try {
			this.csarFile = repo.getbyId(csarFileId);
		} catch(PersistenceException e) {
			AbstractServlet.addError(e.getMessage());
		}
	}
	
	public CsarFile getResult() {
		super.logInvalidResultAccess("getResult");
		
		return this.csarFile;
	}
}
