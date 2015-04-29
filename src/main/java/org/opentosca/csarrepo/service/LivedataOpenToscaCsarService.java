package org.opentosca.csarrepo.service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.opentosca.csarrepo.exception.DeploymentException;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.util.ContainerApiClient;
import org.opentosca.csarrepo.util.DeployedCsarObject;
import org.opentosca.csarrepo.util.PlanInvocationHelper;
import org.opentosca.csarrepo.util.jaxb.SimpleXLink;

/**
 * Service which returns a List containing information of deployed CSARs inside
 * an existing OpenTOSCA-Server
 * 
 * @author Dennis Przytarski, Thomas Kosch
 */
public class LivedataOpenToscaCsarService extends AbstractService {

	private List<DeployedCsarObject> deployedCsars = new ArrayList<DeployedCsarObject>();

	/**
	 * @param userId
	 */
	public LivedataOpenToscaCsarService(long userId, OpenToscaServer openToscaServer) {
		super(userId);
		try {
			ContainerApiClient client = new ContainerApiClient(openToscaServer);
			List<SimpleXLink> links = client.getDeployedCsars();

			for (SimpleXLink link : links) {
				Long id = PlanInvocationHelper.getCsarFileId(openToscaServer, link.getTitle());
				if (null != id) {
					deployedCsars.add(new DeployedCsarObject(id, link.getTitle()));
				}
			}
		} catch (URISyntaxException | DeploymentException e) {
			this.addError(e.getMessage());
		}
	}

	/**
	 * @return
	 * @return List of Instances Information
	 */
	public List<DeployedCsarObject> getResult() {
		super.logInvalidResultAccess("getResult");

		return deployedCsars;
	}

}
