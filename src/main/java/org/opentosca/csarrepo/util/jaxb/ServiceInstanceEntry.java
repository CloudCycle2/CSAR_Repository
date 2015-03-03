package org.opentosca.csarrepo.util.jaxb;

import java.net.URI;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.opentosca.csarrepo.rest.model.SimpleXLink;

/**
 * 
 * @author Marcus Eisele <marcus.eisele@gmail.com>
 *
 */
@XmlRootElement(name = "ServiceInstance")
public class ServiceInstanceEntry {

	private URI serviceInstanceID;
	private String csarID;
	private String serviceTemplateID;
	private String serviceTemplateName;
	private Date created;
	private List<SimpleXLink> links = new LinkedList<SimpleXLink>();

	private List<SimpleXLink> nodeInstanceList = new LinkedList<SimpleXLink>();

	protected ServiceInstanceEntry() {
		super();
	}

	@XmlAttribute(name = "serviceInstanceID", required = true)
	public URI getServiceInstanceID() {
		return serviceInstanceID;
	}

	@XmlAttribute(name = "csarID", required = true)
	public String getCsarID() {
		return csarID;
	}

	@XmlAttribute(name = "serviceTemplateID", required = true)
	public String getServiceTemplateID() {
		return serviceTemplateID;
	}

	@XmlAttribute(name = "serviceTemplateName")
	public String getServiceTemplateName() {
		return serviceTemplateName;
	}

	@XmlAttribute(name = "created-at")
	public Date getCreated() {
		return created;
	}

	@XmlElement(name = "Link")
	public List<SimpleXLink> getLinks() {
		return links;
	}

	@XmlElementWrapper(name = "nodeInstances")
	@XmlElement(name = "nodeInstance")
	public List<SimpleXLink> getNodeInstanceList() {
		return nodeInstanceList;
	}

	public void setServiceInstanceID(URI serviceInstanceID) {
		this.serviceInstanceID = serviceInstanceID;
	}

	public void setCsarID(String csarID) {
		this.csarID = csarID;
	}

	public void setServiceTemplateID(String serviceTemplateID) {
		this.serviceTemplateID = serviceTemplateID;
	}

	public void setServiceTemplateName(String serviceTemplateName) {
		this.serviceTemplateName = serviceTemplateName;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setLinks(List<SimpleXLink> links) {
		this.links = links;
	}

	public void setNodeInstanceList(List<SimpleXLink> nodeInstanceList) {
		this.nodeInstanceList = nodeInstanceList;
	}

	@Override
	public String toString() {
		return String.format("id: %s / csarID: %s / serviceTemplateName: %s", this.serviceInstanceID, this.csarID,
				this.serviceTemplateID);
	}

}
