package org.opentosca.csarrepo.service;

import java.util.List;

import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.repository.CsarRepository;

/**
 * @author Dennis Przytarski
 */
public class ListCsarService extends AbstractService {

	private final CsarRepository csarRepository = new CsarRepository();

	/**
	 * @param userId
	 */
	public ListCsarService(long userId) {
		super(userId);
	}

	/**
	 * @return List of CSARs
	 */
	public List<Csar> getResult() {
		try {
			return csarRepository.getAll();
		} catch (Exception e) {
			this.addError(e.getMessage());
		}
		return null;
	}
}
