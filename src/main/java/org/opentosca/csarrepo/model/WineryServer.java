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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.opentosca.csarrepo.model.join.CsarWineryServer;
import org.opentosca.csarrepo.model.join.UserWineryServer;

/**
 * 
 * Hibernate annotated class for Winery Server
 * 
 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
 *
 */

/**
 * @author kosch
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
	private URL address;

	@Column(name = "name")
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "csarWineryServerId.wineryServer")
	private List<CsarWineryServer> csarWineryServer;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userWineryServerId.wineryServer")
	private List<UserWineryServer> userWineryServer = new ArrayList<UserWineryServer>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * This method maps an user instance to the corresponding wineryServer in
	 * the database
	 * 
	 * @param wineryServer
	 *            A wineryServer object
	 * 
	 */
	public void addUser(User user) {
		UserWineryServer userWineryServer = new UserWineryServer(new UserWineryServer.UserWineryServerId(user, this));

		userWineryServer.setUser(user);
		userWineryServer.setWineryServer(this);

		this.userWineryServer.add(userWineryServer);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the address
	 */
	public URL getAddress() {
		return this.address;
	}

	/**
	 * @param address
	 *            to set
	 */
	public void setAddress(URL address) {
		this.address = address;
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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name of the winery instance
	 */
	public void setName(String name) {
		this.name = name;
	}

}
