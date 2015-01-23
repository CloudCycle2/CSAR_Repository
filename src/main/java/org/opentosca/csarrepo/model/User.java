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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.opentosca.csarrepo.model.join.CsarUser;
import org.opentosca.csarrepo.model.join.OpenToscaServerUser;
import org.opentosca.csarrepo.model.join.UserWineryServer;

/**
 * Hibernate annotated class for the user
 * 
 * @author Alexander Blehm, Thomas Kosch (mail@thomaskosch.com), Dennis
 *         Przytarski
 *
 */
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private long id;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "password")
	private String password;

	@Column(name = "mail")
	private String mail;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "csarUserId.user")
	private List<CsarUser> csarUser;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "openToscaServerUserId.user")
	private List<OpenToscaServerUser> openToscaServerUser = new ArrayList<OpenToscaServerUser>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userWineryServerId.user")
	private List<UserWineryServer> userWineryServer = new ArrayList<UserWineryServer>();

	/**
	 * This method maps an openToscaServer instance to the corresponding user in
	 * the database
	 * 
	 * @param openToscaServer
	 *            A openToscaServer object
	 * 
	 */
	public void addOpenToscaServer(OpenToscaServer openToscaServer) {
		OpenToscaServerUser openToscaServerUser = new OpenToscaServerUser(
				new OpenToscaServerUser.OpenToscaServerUserId(openToscaServer, this));

		openToscaServerUser.setOpenToscaServer(openToscaServer);
		openToscaServerUser.setUser(this);

		this.openToscaServerUser.add(openToscaServerUser);
	}

	/**
	 * This method maps an wineryServer instance to the corresponding user in
	 * the database
	 * 
	 * @param wineryServer
	 *            A wineryServer object
	 * 
	 */
	public void addWineryServer(WineryServer wineryServer) {
		UserWineryServer userWineryServer = new UserWineryServer(new UserWineryServer.UserWineryServerId(this,
				wineryServer));

		userWineryServer.setUser(this);
		userWineryServer.setWineryServer(wineryServer);

		this.userWineryServer.add(userWineryServer);
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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 *            to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
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

	/**
	 * @return List containing the correlation of the respective classes
	 */
	public List<UserWineryServer> getUserWineryServer() {
		return userWineryServer;
	}

	/**
	 * @param userWineryServer
	 *            List containing the correlation of the respective classes
	 */
	public void setUserWineryServer(List<UserWineryServer> userWineryServer) {
		this.userWineryServer = userWineryServer;
	}

}
