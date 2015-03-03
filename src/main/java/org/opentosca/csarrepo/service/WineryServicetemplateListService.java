package org.opentosca.csarrepo.service;

import java.net.URL;
import java.util.List;

import org.opentosca.csarrepo.util.Servicetemplate;
import org.opentosca.csarrepo.util.WineryApiClient;

public class WineryServicetemplateListService extends AbstractService {

	private List<Servicetemplate> result;
	
	public WineryServicetemplateListService(long userId, URL wineryAddress) {
		super(userId);
		
		WineryApiClient client = new WineryApiClient(wineryAddress);
		try {
			this.result = client.getServiceTemplates();
		} catch (Exception e) {
			this.addError(e.getMessage());
		}
	}
	
	public List<Servicetemplate> getResult() {
		super.logInvalidResultAccess("getResult");
		
		return this.result;
	}

}
