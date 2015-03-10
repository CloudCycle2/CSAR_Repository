package org.opentosca.csarrepo.util;

import java.io.File;

import org.junit.Test;

public class ZipTest {

	private File fileToCsar;
	private final static String ORIGINAL_CSAR = "/TestLifeCycle.csar";

	@Test
	public void testRemoveFileFromZip() {
		// assertTrue(new TFile(ORIGINAL_CSAR).list(new File(ORIGINAL_CSAR +
		// "/TOSCA-Metadata/TOSCA.me")));

		// System.out.println(new
		// TFile(getClass().getResource(ORIGINAL_CSAR).toString()).list());
		// System.out.println(getClass().getResource(ORIGINAL_CSAR));
		System.out.println(ZipTest.class.getResource(ORIGINAL_CSAR));
	}
	// @Test
	// public void testDeleteFileFromZip() {
	//
	// }

}
