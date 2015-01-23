package org.opentosca.csarrepo.model;

import java.net.URL;
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
import javax.persistence.ManyToOne;
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

	@Column(name = "address")
	private URL address;

	@Column(name = "instance_id")
	private long instanceId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "csar_file_id")
	private CsarFile csarFile;

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
	 * @return get address of cloud instance
	 */
	public URL getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            set address of the cloud instance
	 */
	public void setAddress(URL address) {
		this.address = address;
	}

	/**
	 * @return The Csarfile
	 */
	public CsarFile getCsarFile() {
		return csarFile;
	}

	/**
	 * @param csarFile
	 *            The Csarfile
	 */
	public void setCsarFile(CsarFile csarFile) {
		this.csarFile = csarFile;
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
