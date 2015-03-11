/**
 * 
 */
package org.opentosca.csarrepo.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.junit.Test;
import org.opentosca.csarrepo.exception.DeploymentException;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.util.jaxb.ServiceInstanceEntry;
import org.opentosca.csarrepo.util.jaxb.SimpleXLink;

/**
 * Tests the connectivity and parsing ability.
 *
 * => Don't let if fail - it depends on a running OT Container.
 * 
 * @author Marcus Eisele, Dennis Przytarski
 *
 */
public class ContainerApiClientTest {

	private static final String CONTAINERAPI_ADDRESS = "http://192.168.209.249:1337/containerapi";

	@Test
	public void testServiceInstances() throws URISyntaxException, MalformedURLException, DeploymentException {
		URL url = new URL(CONTAINERAPI_ADDRESS);
		OpenToscaServer openToscaServer = new OpenToscaServer();
		openToscaServer.setAddress(url);
		ContainerApiClient containerApiClient = new ContainerApiClient(openToscaServer);
		List<ServiceInstanceEntry> serviceInstances = containerApiClient.getServiceInstances();
		for (ServiceInstanceEntry serviceInstance : serviceInstances) {
			System.out.println(serviceInstance);
		}
	}

	@Test
	public void testDeployedCsars() throws URISyntaxException, MalformedURLException, DeploymentException {
		URL url = new URL(CONTAINERAPI_ADDRESS);
		OpenToscaServer openToscaServer = new OpenToscaServer();
		openToscaServer.setAddress(url);
		ContainerApiClient containerApiClient = new ContainerApiClient(openToscaServer);
		List<SimpleXLink> deployedCsars = containerApiClient.getDeployedCsars();
		for (SimpleXLink deployedCsar : deployedCsars) {
			System.out.println(deployedCsar);
		}
	}

	@Test
	public void testGetRepositoryCsarFileIdFromCsarName() throws MalformedURLException, URISyntaxException,
			DeploymentException {
		URL url = new URL(CONTAINERAPI_ADDRESS);
		OpenToscaServer openToscaServer = new OpenToscaServer();
		openToscaServer.setAddress(url);
		ContainerApiClient containerApiClient = new ContainerApiClient(openToscaServer);
		Long csarFileId = containerApiClient.getRepositoryCsarFileId("InstallVMServTemplate.csar");
		System.out.println(csarFileId);
	}
}
