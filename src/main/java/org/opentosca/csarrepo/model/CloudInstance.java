package org.opentosca.csarrepo.model;

import java.net.URL;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "csar_file_id")
	private CsarFile csarFile;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "open_tosca_server_id")
	private OpenToscaServer openToscaServer;

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
	 * @return the instance id
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param instanceId
	 *            the instance id
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the OpenTosca server associated with this cloud instance
	 */
	public OpenToscaServer getOpenToscaServer() {
		return openToscaServer;
	}

	/**
	 * @param openToscaServer
	 *            An OpenTosca server associated with this cloud instance
	 */
	public void setOpenToscaServer(OpenToscaServer openToscaServer) {
		this.openToscaServer = openToscaServer;
	}
}
