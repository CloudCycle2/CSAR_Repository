package org.opentosca.csarrepo.rest.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Abstract entry class for the csarrepo
 *
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
@XmlRootElement(name = "abstract-entry")
public abstract class AbstractEntry {
	private String version = "0.1";

	private List<SimpleXLink> links = new LinkedList<SimpleXLink>();

	private List<SimpleXLink> children;

	protected AbstractEntry() {
		super();
	}

	public AbstractEntry(List<SimpleXLink> links, List<SimpleXLink> children) {
		super();
		this.links = links;
		this.children = children;
	}

	@XmlAttribute(name = "version", required = true)
	public String getVersion() {
		return version;
	}

	@XmlElementWrapper(name = "links")
	@XmlElement(name = "link")
	public List<SimpleXLink> getLinks() {
		return links;
	}

	// this is transient to ensure proper naming in subclasses
	@XmlTransient
	public List<SimpleXLink> getChildren() {
		return children;
	}

}
