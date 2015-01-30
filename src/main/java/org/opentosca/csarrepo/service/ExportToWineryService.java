package org.opentosca.csarrepo.service;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.WineryServer;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.model.repository.WineryServerRepository;
import org.opentosca.csarrepo.util.WineryApiClient;

public class ExportToWineryService extends AbstractService {

	private boolean succeded = false;
	
	public ExportToWineryService(long userId, long wineryId, long fileId) {
		super(userId);
		
		CsarFile csarFile = new CsarFile();
		WineryServer wineryServer = new WineryServer();
		
		// load and validate file
		CsarFileRepository csarFileRepo = new CsarFileRepository();
		try {
			csarFile = csarFileRepo.getbyId(fileId);
		} catch (PersistenceException e) {
			System.out.println("loading file info failed");
			this.addError("loading file info failed");
		}
		
		// load and validate winery server
		WineryServerRepository wineryServerRepo = new WineryServerRepository();
		try {
			wineryServer = wineryServerRepo.getbyId(wineryId);
		} catch(PersistenceException e) {
			System.out.println("loading winery failed");
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
		}
		
		this.succeded = true;
	}
	
	public boolean getResult() {
		this.logInvalidResultAccess("getReult");
		
		return this.succeded;
	}

}
