package org.opentosca.csarrepo.model.join;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.model.WineryServer;

/**
 * Join entity of the entity User and WineryServer
 *
 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
 *
 */
@Entity
@Table(name = "user_winery_server")
public class UserWineryServer {

	@EmbeddedId
	private UserWineryServerId userWineryServerId;

	public UserWineryServer() {
	}

	public UserWineryServer(UserWineryServerId userWineryServerId) {
		this.userWineryServerId = userWineryServerId;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "winery_server_id", insertable = false, updatable = false)
	private WineryServer wineryServer;

	/**
	 * @param user
	 *            The User
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param wineryServer
	 *            the WineryServer
	 */
	public void setWineryServer(WineryServer wineryServer) {
		this.wineryServer = wineryServer;
	}

	/**
	 * Embedded class for wrapping composite primary keys
	 *
	 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
	 *
	 */
	@Embeddable
	public static class UserWineryServerId implements Serializable {

		private static final long serialVersionUID = 1L;

		@ManyToOne
		@JoinColumn(name = "user_id")
		private User user;

		@ManyToOne
		@JoinColumn(name = "winery_server_id")
		private WineryServer wineryServer;

		public UserWineryServerId() {
		}

		public UserWineryServerId(User user, WineryServer wineryServer) {
			this.user = user;
			this.wineryServer = wineryServer;
		}

		@Override
		public boolean equals(Object instance) {
			if (null == instance) {
				return false;
			}

			if (!(instance instanceof UserWineryServerId)) {
				return false;
			}

			final UserWineryServerId other = (UserWineryServerId) instance;

			if (!(user.getId().equals(other.getUser().getId()))) {
				return false;
			}

			if (!(wineryServer.getId().equals(other.getWineryServer().getId()))) {
				return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			return this.user.hashCode() ^ this.wineryServer.hashCode();
		}

		/**
		 * @return the User
		 */
		public User getUser() {
			return user;
		}

		/**
		 * @return the WineryServer
		 */
		public WineryServer getWineryServer() {
			return wineryServer;
		}

	}

}
