package org.opentosca.csarrepo.service;

import java.net.URISyntaxException;
import java.util.ArrayList;

import org.opentosca.csarrepo.exception.DeploymentException;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.util.ContainerApiClient;
import org.opentosca.csarrepo.util.jaxb.SimpleXLink;

/**
 * Service which returns a List containing information of deployed CSARs inside
 * an existing OpenTOSCA-Server
 * 
 * @author Dennis Przytarski, Thomas Kosch
 */
public class ListLivedataOpenToscaCsarService extends AbstractService {

	private ArrayList<SimpleXLink> deployedCsars;

	/**
	 * @param userId
	 */
	public ListLivedataOpenToscaCsarService(long userId, OpenToscaServer openToscaServer) {
		super(userId);
		try {
			ContainerApiClient client = new ContainerApiClient(openToscaServer);
			deployedCsars = client.getDeployedCsars();
		} catch (URISyntaxException | DeploymentException e) {
			this.addError(e.getMessage());
		}
	}

	/**
	 * @return
	 * @return List of Instances Information
	 */
	public ArrayList<SimpleXLink> getResult() {
		super.logInvalidResultAccess("getResult");

		return deployedCsars;
	}

}
