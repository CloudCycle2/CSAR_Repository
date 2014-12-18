package org.opentosca.csarrepo.rest.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entry class for the csarrepo root
 *
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
@XmlRootElement(name = "root-entry")
public class RootEntry {
	private String version = "0.1";

	private List<SimpleXLink> links = new LinkedList<SimpleXLink>();

	protected RootEntry() {
		super();
	}

	public RootEntry(List<SimpleXLink> links) {
		super();
		this.links = links;
	}

	@XmlAttribute(name = "version", required = true)
	public String getVersion() {
		return version;
	}

	@XmlElement(name = "link")
	public List<SimpleXLink> getLinks() {
		return links;
	}
}
