package org.opentosca.csarrepo.model.repository;

import org.junit.Test;
import org.opentosca.csarrepo.service.EditCsarNameService;

/**
 * Test case for evaluating renaming Csars
 * 
 * @author Thomas Kosch (mail@thomaskosch.com)
 *
 */
public class RenameCsarTest {

	private long id = 1;
	private String updatedName = "Cool";
	private EditCsarNameService editCsarNameService;

	@Test
	public void renameCsarTest() {
		// Retrieve Csar from database
		editCsarNameService = new EditCsarNameService(id, 0, updatedName);
		editCsarNameService.getResult();
	}
}
