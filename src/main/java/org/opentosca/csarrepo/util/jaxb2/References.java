package org.opentosca.csarrepo.util.jaxb2;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.opentosca.csarrepo.util.jaxb.SimpleXLink;

/**
 * 
 * @author Dennis Przytarski, Thomas Kosch
 *
 */
@XmlRootElement(name = "References")
public class References {

	private List<SimpleXLink> links = new ArrayList<SimpleXLink>();

	@XmlElements(value = { @XmlElement(name = "Reference") })
    public List<SimpleXLink> getLinks() {
        return links;
    }

}
