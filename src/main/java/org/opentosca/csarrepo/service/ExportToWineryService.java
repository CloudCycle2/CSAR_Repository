package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.WineryServer;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.model.repository.WineryServerRepository;
import org.opentosca.csarrepo.util.WineryApiClient;

public class ExportToWineryService extends AbstractService {

	private boolean succeded = false;
	private static final Logger LOGGER = LogManager.getLogger(ExportToWineryService.class);
	
	public ExportToWineryService(long userId, long wineryId, long fileId) {
		super(userId);
		
		CsarFile csarFile = null;
		WineryServer wineryServer = null;
		
		// load and validate file
		CsarFileRepository csarFileRepo = new CsarFileRepository();
		try {
			csarFile = csarFileRepo.getbyId(fileId);
		} catch (PersistenceException e) {
			this.addError("loading file info failed");
		}
		
		// load and validate winery server
		WineryServerRepository wineryServerRepo = new WineryServerRepository();
		try {
			wineryServer = wineryServerRepo.getbyId(wineryId);
		} catch(PersistenceException e) {
			this.addError("loading winery failed");
		}
		
		if(this.hasErrors()) {
			// validation errors --> return
			return;
		}
		
		// winery and csar file exist --> upload
		WineryApiClient client = new WineryApiClient(wineryServer.getAddress());
		try {
			client.uploadToWinery(csarFile);
		} catch (Exception e) {
			this.addError("Upload failed");
			LOGGER.error("Failed to push to winery");
		}
		
		this.succeded = true;
	}
	
	public boolean getResult() {
		this.logInvalidResultAccess("getReult");
		
		return this.succeded;
	}

}
