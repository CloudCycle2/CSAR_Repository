package org.opentosca.csarrepo.service;

import java.util.List;

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
		} catch (Exception e) {
			this.addError(e.getMessage());
		}
	}

	/**
	 * @return List of CSARs
	 */
	public List<CsarFile> getResult() {
		return csarFiles;
	}

}
