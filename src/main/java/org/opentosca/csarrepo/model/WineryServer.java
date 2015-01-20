package org.opentosca.csarrepo.model;

import java.net.URI;
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
 * Hibernate annotated class for Winery Server
 * 
 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
 *
 */

@Entity
@Table(name = "winery_server")
public class WineryServer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "winery_server_id")
	private long id;

	@Column(name = "address")
	private String address;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "wineryServers")
	private List<Csar> csars;

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
	public URI getAddress() {
		return URI.create(address);
	}

	/**
	 * @param address
	 *            to set
	 */
	public void setAddress(URI address) {
		this.address = address.toString();
	}

	/**
	 * @return the csars
	 */
	public List<Csar> getCsars() {
		return csars;
	}

	/**
	 * @param csars
	 *            to set
	 */
	public void setCsars(List<Csar> csars) {
		this.csars = csars;
	}

	/**
	 * @return the users
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
