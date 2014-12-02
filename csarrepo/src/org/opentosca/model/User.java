package org.opentosca.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * This is a first example for a model using hibernate
 * 
 * @author Alexander Blehm
 *
 */
@Entity
public class User {
	
	private long id;
	private String userName;
	private String userPassword;
	private String userMail;
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}
	/**
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	/**
	 * @return the userMail
	 */
	public String getUserMail() {
		return userMail;
	}
	/**
	 * @param userMail the userMail to set
	 */
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	
	

	
	
}
