package org.opentosca.csarrepo.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.opentosca.csarrepo.model.Csar;

/**
 * Entry class for the csarrepo root
 *
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
@XmlRootElement(name = "csar")
public class CsarEntry extends AbstractEntry {

	private long id;

	private String name;

	protected CsarEntry() {
		super();
	}

	public CsarEntry(Csar csar, List<SimpleXLink> links, List<SimpleXLink> children) {
		super(links, children);
		this.id = csar.getId();
		this.name = csar.getName();
	}

	@Override
	@XmlElement(name = "link")
	@XmlElementWrapper(name = "csar_files")
	public List<SimpleXLink> getChildren() {
		return super.getChildren();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
