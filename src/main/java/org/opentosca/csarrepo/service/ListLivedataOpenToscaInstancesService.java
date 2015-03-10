package org.opentosca.csarrepo.service;

import java.net.URISyntaxException;
import java.util.List;

import org.opentosca.csarrepo.exception.DeploymentException;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.util.ContainerApiClient;
import org.opentosca.csarrepo.util.jaxb.ServiceInstanceEntry;

/**
 * Service which returns a List containing information about running Instances
 * inside an existing OpenTOSCA-Server
 * 
 * @author Marcus Eisele (marcus.eisele@gmail.com)
 */
public class ListLivedataOpenToscaInstancesService extends AbstractService {

	private List<ServiceInstanceEntry> runningLiveInstances;

	/**
	 * @param userId
	 */
	public ListLivedataOpenToscaInstancesService(long userId, OpenToscaServer openToscaServer) {
		super(userId);
		try {
			ContainerApiClient client;
			client = new ContainerApiClient(openToscaServer);
			runningLiveInstances = client.getServiceInstances();
		} catch (URISyntaxException | DeploymentException e) {
			this.addError(e.getMessage());
		}
	}

	/**
	 * @return
	 * @return List of Instances Information
	 */
	public List<ServiceInstanceEntry> getResult() {
		super.logInvalidResultAccess("getResult");

		return runningLiveInstances;
	}

}
