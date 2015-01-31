package org.opentosca.csarrepo.model.repository;

import org.junit.Test;
import org.opentosca.csarrepo.service.CreateOpenToscaServerService;
import org.opentosca.csarrepo.service.CreateUserService;

/**
 * Testcases for the OpenToscaServer 
 * 
 * @author Marcus Eisele (marcus.eisele@gmail.com)
 *
 */

public class OpenToscaRepositoryTest {

	private CreateOpenToscaServerService createOpenToscaService;

	@Test
	public void testCreateOT() {
		createOpenToscaService = new CreateOpenToscaServerService(1L, "localhost", "http://localhost:1337/containerapi");
	}
}
