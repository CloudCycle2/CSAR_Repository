package org.opentosca.csarrepo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.opentosca.csarrepo.model.join.CsarUser;

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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private List<OpenToscaServer> openToscaServers;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private List<WineryServer> wineryServers;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "csarUserId.user")
	private List<CsarUser> csarUser = new ArrayList<CsarUser>();

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
	 * @return A list with OpenTosca servers
	 */
	public List<OpenToscaServer> getOpenToscaServers() {
		return openToscaServers;
	}

	/**
	 * @param openToscaServers
	 *            A list containing OpenTosca servers
	 */
	public void setOpenToscaServers(List<OpenToscaServer> openToscaServers) {
		this.openToscaServers = openToscaServers;
	}

	/**
	 * @return the winery servers
	 */
	public List<WineryServer> getWineryServers() {
		return wineryServers;
	}

	/**
	 * @param wineryServers
	 *            to set
	 */
	public void setWineryServers(List<WineryServer> wineryServers) {
		this.wineryServers = wineryServers;
	}

}
