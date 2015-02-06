package org.opentosca.csarrepo.model.repository;

import org.junit.Test;
import org.opentosca.csarrepo.service.DeleteUserService;

/**
 * Testing class for testing the <code>DeleteUserService</code>
 * 
 * @author Thomas Kosch, (mail@thomaskosch.com)
 *
 */
public class DeleteUserServiceTest {

	private DeleteUserService deleteUserService;

	@Test
	public void deleteUser() {
		deleteUserService = new DeleteUserService(1, 1);
	}

}
