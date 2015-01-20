package org.opentosca.csarrepo.model.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opentosca.csarrepo.model.Csar;

/**
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
public class CsarRepositoryTest {

	private List<Csar> csars = new ArrayList<Csar>();
	private CsarRepository csarRepo = new CsarRepository();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Csar csar1 = new Csar();
		csar1.setName("csar1");

		Csar csar2 = new Csar();
		csar2.setName("csar2");
		Csar csar3 = new Csar();
		csar3.setName("csar3");

		csars.add(csar1);
		csars.add(csar2);
		csars.add(csar3);
		csarRepo.save(csar1);
		csarRepo.save(csar2);
		csarRepo.save(csar3);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link org.opentosca.csarrepo.model.repository.CsarRepository#getbyId(long)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetbyId() throws Exception {
		for (Csar csar : csars) {
			Csar csarRetrieved = csarRepo.getbyId(csar.getId());
			// TODO: compare csars directly (using .equals(..))
			// compare ids because equals isn't implemented yet
			assertEquals("Didnt retrieve correct element", csar.getId(), csarRetrieved.getId());
		}
	}

	/**
	 * Test method for
	 * {@link org.opentosca.csarrepo.model.repository.CsarRepository#getAll()}.
	 */
	@Test
	public void testGetAll() throws Exception {
		List<Csar> all = csarRepo.getAll();
		int size = all.size();
		Csar csar = new Csar();
		csar.getName();
		csarRepo.save(csar);
		all = csarRepo.getAll();
		assertEquals("size mismatch after addition", size + 1, all.size());
		csarRepo.delete(csar);
		all = csarRepo.getAll();
		assertEquals("size mismatch after deletion", size, all.size());
	}
}
