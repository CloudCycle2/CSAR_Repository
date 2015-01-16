package org.opentosca.csarrepo.model;

import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * Hibernate Annotated class for Csar
 * 
 * @author eiselems (marcus.eisele@gmail.com), Thomas Kosch
 *         (mail@thomaskosch.com), Dennis Przytarski
 *
 */
@Entity
@Table(name = "csar")
public class Csar {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "csar_id")
	private long id;

	@Column(name = "name")
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "csar")
	private List<CsarFile> csarFiles;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "csar_cloud_instance", joinColumns = { @JoinColumn(name = "csar_id") }, inverseJoinColumns = { @JoinColumn(name = "cloud_instance_id") })
	private List<CloudInstance> cloudInstances;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "csar_open_tosca_server", joinColumns = { @JoinColumn(name = "csar_id") }, inverseJoinColumns = { @JoinColumn(name = "open_tosca_server_id") })
	private List<OpenToscaServer> openToscaServers;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "csar_winery_server", joinColumns = { @JoinColumn(name = "csar_id") }, inverseJoinColumns = { @JoinColumn(name = "winery_server_id") })
	private List<WineryServer> wineryServers;

	public Csar() {
		this.csarFiles = new ArrayList<CsarFile>();
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns all CSAR files of the current CSAR
	 * 
	 * @return the list of CSAR files
	 */
	public List<CsarFile> getCsarFiles() {
		return csarFiles;
	}

	/**
	 * @return the cloud instances
	 */
	public List<CloudInstance> getCloudInstances() {
		return cloudInstances;
	}

	/**
	 * @param cloudInstances
	 *            the cloud instances
	 */
	public void setCloudInstances(List<CloudInstance> cloudInstances) {
		this.cloudInstances = cloudInstances;
	}

	/**
	 * @return a list containing OpenTosca servers
	 */
	public List<OpenToscaServer> getOpenToscaServers() {
		return openToscaServers;
	}

	/**
	 * @param openToscaServers
	 *            A list containing OpenTosca servers
	 */
	public void setOpenToscaServer(List<OpenToscaServer> openToscaServers) {
		this.openToscaServers = openToscaServers;
	}

	/**
	 * @return A list containing Winery servers
	 */
	public List<WineryServer> getWineryServers() {
		return wineryServers;
	}

	/**
	 * @param wineryServers
	 *            A list containing Winery servers
	 */
	public void setWineryServers(List<WineryServer> wineryServers) {
		this.wineryServers = wineryServers;
	}
}
