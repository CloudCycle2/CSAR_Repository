package org.opentosca.csarrepo.util.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Dennis Przytarski, Thomas Kosch
 *
 */
@XmlRootElement(name = "References", namespace = "")
public class DeployedCsars {

	private List<SimpleXLink> links = new ArrayList<SimpleXLink>();

	@XmlElements(value = { @XmlElement(name = "Reference", namespace = "") })
	public List<SimpleXLink> getLinks() {
		return links;
	}

}
