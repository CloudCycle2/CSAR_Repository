/**
 * 
 */
package org.opentosca.csarrepo.model.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.opentosca.csarrepo.util.StringUtils;

/**
 * @author Marcus Eisele (marcus.eisele@gmail.com)
 *
 */
public class StringUtilsTest {

	@Test
	public void test() {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("a");
		list.add("b");
		list.add("c");
		String join = StringUtils.join(list, ",");
		assertEquals("a,b,a,b,c", join);
	}

}
