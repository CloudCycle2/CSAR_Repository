package org.opentosca.csarrepo.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.opentosca.csarrepo.model.join.CsarOpenToscaServer;
import org.opentosca.csarrepo.model.join.OpenToscaServerUser;

/**
 * 
 * Hibernate annotated class for OpenTosca Server
 * 
 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
 *
 */
@Entity
@Table(name = "open_tosca_server")
public class OpenToscaServer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "open_tosca_server_id")
	private long id;

	@Column(name = "address")
	private URL address;

	@Column(name = "name")
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "openToscaServer")
	private List<CloudInstance> cloudInstances;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "csarOpenToscaServerId.openToscaServer")
	private List<CsarOpenToscaServer> csarOpenToscaServer = new ArrayList<CsarOpenToscaServer>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "openToscaServerUserId.openToscaServer")
	private List<OpenToscaServerUser> openToscaServerUser = new ArrayList<OpenToscaServerUser>();

	public OpenToscaServer() {
	}

	public OpenToscaServer(Long id) {
		this.id = id;
	}

	/**
	 * This method maps an Csar instance to the corresponding OpenTosca in the
	 * database
	 * 
	 * @param csar
	 *            A Csar object
	 * 
	 */
	public void addCsar(Csar csar) {
		CsarOpenToscaServer csarOpenToscaServer = new CsarOpenToscaServer(
				new CsarOpenToscaServer.CsarOpenToscaServerId(csar, this));

		csarOpenToscaServer.setCsar(csar);
		csarOpenToscaServer.setOpenToscaServer(this);

		this.csarOpenToscaServer.add(csarOpenToscaServer);
	}

	/**
	 * This method maps an User instance to the corresponding OpenTosca in the
	 * database
	 * 
	 * @param user
	 *            A user object
	 * 
	 */
	public void addUser(User user) {
		OpenToscaServerUser openToscaServerUser = new OpenToscaServerUser(
				new OpenToscaServerUser.OpenToscaServerUserId(this, user));

		openToscaServerUser.setOpenToscaServer(this);
		openToscaServerUser.setUser(user);

		this.openToscaServerUser.add(openToscaServerUser);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name of the OpenTosca instance
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name of the OpenTosca instance
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public URL getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address
	 */
	public void setAddress(URL address) {
		this.address = address;
	}

	/**
	 * @return cloud instances List containing the correlation of the respective
	 *         classes
	 */
	public List<CloudInstance> getCloudInstances() {
		return cloudInstances;
	}

	/**
	 * @param cloudInstances
	 *            cloud instances
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
	 * @return List containing the correlation of the respective classes
	 */
	public List<OpenToscaServerUser> getOpenToscaServerUser() {
		return openToscaServerUser;
	}

	/**
	 * @param openToscaServerUser
	 *            List containing the correlation of the respective classes
	 */
	public void setOpenToscaServerUser(List<OpenToscaServerUser> openToscaServerUser) {
		this.openToscaServerUser = openToscaServerUser;
	}

}
