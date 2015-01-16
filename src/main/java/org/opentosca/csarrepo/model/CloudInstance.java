package org.opentosca.csarrepo.model;

import java.net.InetAddress;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 * Hibernate annotated class for cloud instances
 * 
 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
 *
 */
@Entity
@Table(name = "cloud_instance")
public class CloudInstance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cloud_instance_id")
	private long id;

	@Column(name = "endpoint_address")
	private InetAddress endpointAddress;

	@Column(name = "instance_id")
	private long instanceId;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "cloudInstances")
	private List<Csar> csars;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "cloud_instance_open_tosca_server", joinColumns = { @JoinColumn(name = "cloud_instance_id") }, inverseJoinColumns = { @JoinColumn(name = "open_tosca_server_id") })
	private List<OpenToscaServer> openToscaServers;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the endpoint
	 */
	public InetAddress getEndpointAddress() {
		return endpointAddress;
	}

	/**
	 * @param endpointAddress
	 *            the endpoint
	 */
	public void setEndpointAddress(InetAddress endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	/**
	 * @return the instance id
	 */
	public long getInstanceId() {
		return instanceId;
	}

	/**
	 * @param instanceId
	 *            the instance id
	 */
	public void setInstanceId(long instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @return list with Csar files
	 */
	public List<Csar> getCsars() {
		return csars;
	}

	/**
	 * @param csars
	 *            List with Csars
	 */
	public void setCsars(List<Csar> csars) {
		this.csars = csars;
	}

	/**
	 * @return all OpenTosca servers associated with this cloud instance
	 */
	public List<OpenToscaServer> getOpenToscaServers() {
		return openToscaServers;
	}

	/**
	 * @param openToscaServers
	 *            A list containing OpenTosca servers associated with this cloud
	 *            instance
	 */
	public void setOpenToscaServers(List<OpenToscaServer> openToscaServers) {
		this.openToscaServers = openToscaServers;
	}
}
