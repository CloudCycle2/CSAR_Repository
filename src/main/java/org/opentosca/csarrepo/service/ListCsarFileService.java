package org.opentosca.csarrepo.service;

import java.util.List;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;

/**
 * @author Dennis Przytarski
 */
public class ListCsarFileService extends AbstractService {

	private List<CsarFile> csarFiles = null;

	/**
	 * @param userId
	 */
	public ListCsarFileService(long userId) {
		super(userId);
		try {
			this.csarFiles = new CsarFileRepository().getAll();
		} catch (PersistenceException e) {
			AbstractServlet.addError(e.getMessage());
		}
	}

	/**
	 * @return List of CSARs
	 */
	public List<CsarFile> getResult() {
		super.logInvalidResultAccess("getResult");

		return this.csarFiles;
	}

}
