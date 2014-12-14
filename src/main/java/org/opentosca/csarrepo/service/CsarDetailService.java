package org.opentosca.csarrepo.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.filesystem.FileSystem;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.repository.CsarRepository;

/**
 * @author Dennis Przytarski, Nikolai Neugebauer
 */
public class CsarDetailService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(CsarDetailService.class);
	
	private List<CsarFile> csarFiles;
	private Csar csar;

	/**
	 * @param userId 
	 * @param csarId csar to load details for
	 */
	public CsarDetailService(long userId, long csarId) {
		super(userId);
		try {
			this.csar = new CsarRepository().getbyId(csarId);
			this.csarFiles = csar.getCsarFiles();
		} catch (Exception e) {
			this.addError(e.getMessage());
		}
	}

	/**
	 * get the csar for the detail view
	 * 
	 * @return Csar
	 */
	public Csar getCsarResult() {
		if(super.hasErrors()) {
			LOGGER.warn("getCsarResult method accessed despite errors");
		}
		
		return this.csar;
	}
	
	/** 
	 * get the list of csar files for the csar
	 * 
	 * @return List of CSARs
	 */
	public List<CsarFile> getCsarFilesResult() {
		if(super.hasErrors()) {
			LOGGER.warn("getCsarFilesResult method accessed despite errors");
		}
		
		return this.csarFiles;
	}

}
