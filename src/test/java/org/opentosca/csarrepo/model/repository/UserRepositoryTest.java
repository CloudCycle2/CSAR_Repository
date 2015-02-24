package org.opentosca.csarrepo.model.repository;

import org.junit.Test;
import org.opentosca.csarrepo.service.CreateUserService;

/**
 * Testcases for the user repository
 * 
 * @author Thomas Kosch (mail@thomaskosch.com)
 *
 */

public class UserRepositoryTest {

	private CreateUserService createUserService;

	@Test
	public void testCreateUser() {
		createUserService = new CreateUserService(1L, "admin", "cloud@cycle.rest", "admin");
	}
}
