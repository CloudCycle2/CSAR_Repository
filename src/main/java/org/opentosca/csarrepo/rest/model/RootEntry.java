package org.opentosca.csarrepo.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entry class for the csarrepo root
 *
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
@XmlRootElement(name = "root-entry")
public class RootEntry extends AbstractEntry {

	protected RootEntry() {
		super();
	}

	public RootEntry(List<SimpleXLink> links) {
		super(links, null);
	}

}
