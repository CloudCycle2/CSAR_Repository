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

	@Column(name = "instance_id")
	private String instanceId;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "openToscaServers")
	private List<Csar> csars;

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
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * @param instanceId
	 *            the instanceId
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @return a list containing all Csars
	 */
	public List<Csar> getCsars() {
		return csars;
	}

	/**
	 * @param csars
	 *            A list containing Csars
	 */
	public void setCsars(List<Csar> csars) {
		this.csars = csars;
	}

	/**
	 * @return cloud instances
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
