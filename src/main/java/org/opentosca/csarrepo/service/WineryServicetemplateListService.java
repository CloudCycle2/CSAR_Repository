package org.opentosca.csarrepo.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.opentosca.csarrepo.util.Servicetemplate;
import org.opentosca.csarrepo.util.WineryApiClient;

public class WineryServicetemplateListService extends AbstractService {

	private List<Servicetemplate> result = new ArrayList<Servicetemplate>();
	
	public WineryServicetemplateListService(long userId, URL wineryAddress) {
		super(userId);
		
		WineryApiClient client = new WineryApiClient(wineryAddress);
		try {
			String json = client.getServiceTemplates();
			JSONArray jsonArray = new JSONArray(json);
			
			for(int i = 0; i < jsonArray.length(); i++) {
				String tmpId = jsonArray.getJSONObject(i).getString("id");
				String tmpNamespace = jsonArray.getJSONObject(i).getString("namespace");
				String tmpName = jsonArray.getJSONObject(i).getString("name");
				
				this.result.add(new Servicetemplate(tmpId, tmpNamespace, tmpName));
			}
		} catch (Exception e) {
			this.addError(e.getMessage());
		}
	}
	
	public List<Servicetemplate> getResult() {
		super.logInvalidResultAccess("getResult");
		
		return this.result;
	}

}
