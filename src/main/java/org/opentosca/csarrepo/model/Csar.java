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

import org.opentosca.csarrepo.model.join.CsarOpenToscaServer;
import org.opentosca.csarrepo.model.join.CsarUser;
import org.opentosca.csarrepo.model.join.CsarWineryServer;

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
	private List<CsarFile> csarFiles = new ArrayList<CsarFile>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "csar_cloud_instance", joinColumns = { @JoinColumn(name = "csar_id") }, inverseJoinColumns = { @JoinColumn(name = "cloud_instance_id") })
	private List<CloudInstance> cloudInstances;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "csarOpenToscaServerId.csar")
	private List<CsarOpenToscaServer> csarOpenToscaServer = new ArrayList<CsarOpenToscaServer>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "csarId")
	private List<CsarWineryServer> csarWineryServer;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "csarId")
	private List<CsarUser> csarUser;

	public Csar(Long id) {
		this.id = id;
	}

	/**
	 * This method maps an OpenTosca instance to the corresponding Csar in the
	 * database
	 * 
	 * @param openToscaServer
	 *            A OpenToscaServer object instance
	 */
	public void addOpenToscaServer(OpenToscaServer openToscaServer) {
		CsarOpenToscaServer csarOpenToscaServer = new CsarOpenToscaServer(
				new CsarOpenToscaServer.CsarOpenToscaServerId(this, openToscaServer));

		csarOpenToscaServer.setCsar(this);
		csarOpenToscaServer.setOpenToscaServer(openToscaServer);

		this.csarOpenToscaServer.add(csarOpenToscaServer);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
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
	 * @return List containing the correlation of the respective classes
	 */
	public List<CsarOpenToscaServer> getCsarOpenToscaServer() {
		return csarOpenToscaServer;
	}

	/**
	 * @param csarOpenToscaServer
	 *            List containing the correlation of the respective classes
	 */
	public void setCsarOpenToscaServer(List<CsarOpenToscaServer> csarOpenToscaServer) {
		this.csarOpenToscaServer = csarOpenToscaServer;
	}

	/**
	 * @param csarFiles
	 *            List containing the correlation of the respective classes
	 */
	public void setCsarFiles(List<CsarFile> csarFiles) {
		this.csarFiles = csarFiles;
	}

	/**
	 * @return List containing the correlation of the respective classes
	 */
	public List<CsarWineryServer> getCsarWineryServer() {
		return csarWineryServer;
	}

	/**
	 * @param csarWineryServer
	 *            List containing the correlation of the respective classes
	 */
	public void setCsarWineryServer(List<CsarWineryServer> csarWineryServer) {
		this.csarWineryServer = csarWineryServer;
	}

	/**
	 * @return List containing the correlation of the respective classes
	 */
	public List<CsarUser> getCsarUser() {
		return csarUser;
	}

	/**
	 * @param csarUser
	 *            List containing the correlation of the respective classes
	 */
	public void setCsarUser(List<CsarUser> csarUser) {
		this.csarUser = csarUser;
	}

}
