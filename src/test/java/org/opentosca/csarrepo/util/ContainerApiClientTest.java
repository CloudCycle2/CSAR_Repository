/**
 * 
 */
package org.opentosca.csarrepo.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.junit.Test;
import org.opentosca.csarrepo.exception.DeploymentException;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.util.jaxb.ServiceInstanceEntry;

/**
 * Tests the connectivity and parsing ability
 * 
 * @author Marcus Eisele
 *
 */
public class ContainerApiClientTest {

	@Test
	public void test() throws URISyntaxException, MalformedURLException, DeploymentException {
		// String pathToTest =
		// "http://192.168.209.224:1337/containerapi/instancedata/serviceInstances";
		URL url = new URL("http://192.168.209.22:1337/containerapi");
		OpenToscaServer openToscaServer = new OpenToscaServer();
		openToscaServer.setAddress(url);
		ContainerApiClient containerApiClient = new ContainerApiClient(openToscaServer);
		ArrayList<ServiceInstanceEntry> runningLiveInstances = containerApiClient.getRunningLiveInstances();
		for (ServiceInstanceEntry serviceInstanceEntry : runningLiveInstances) {
			System.out.println(serviceInstanceEntry);
		}

		// don't let if fail - it depends on a running OT Container
	}
}
