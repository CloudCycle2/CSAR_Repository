package org.opentosca.csarrepo.model;

import java.net.URL;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "openToscaServerId")
	private List<CsarOpenToscaServer> csarOpenToscaServer;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "openToscaServers")
	private List<CloudInstance> cloudInstances;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
