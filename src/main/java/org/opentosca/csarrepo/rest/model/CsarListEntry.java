package org.opentosca.csarrepo.rest.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entry class for the csarrepo root
 *
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
@XmlRootElement(name = "csar-list")
public class CsarListEntry extends AbstractEntry {
	private List<SimpleXLink> links = new LinkedList<SimpleXLink>();

	protected CsarListEntry() {
		super();
	}

	public CsarListEntry(List<SimpleXLink> links, List<SimpleXLink> children) {
		super(links, children);
	}

	@Override
	@XmlElement(name = "link")
	@XmlElementWrapper(name = "csars")
	public List<SimpleXLink> getChildren() {
		return super.getChildren();
	}
}
