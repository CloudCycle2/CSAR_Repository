package org.opentosca.csarrepo.model.join;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.User;

/**
 * Join entity of the entity OpenToscaServer and User
 *
 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
 *
 */
@Entity
@Table(name = "open_tosca_server_user")
public class OpenToscaServerUser {

	@EmbeddedId
	private OpenToscaServerUserId openToscaServerUserId;

	public OpenToscaServerUser() {
	}

	public OpenToscaServerUser(OpenToscaServerUserId openToscaServerUserId) {
		this.openToscaServerUserId = openToscaServerUserId;
	}

	@ManyToOne
	@JoinColumn(name = "open_tosca_server_id", insertable = false, updatable = false)
	private OpenToscaServer openToscaServer;

	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	/**
	 * @param openToscaServer
	 *            the OpenToscaServer
	 */
	public void setOpenToscaServer(OpenToscaServer openToscaServer) {
		this.openToscaServer = openToscaServer;
	}

	/**
	 * @param user
	 *            The User
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Embedded class for wrapping composite primary keys
	 *
	 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
	 *
	 */
	@Embeddable
	public static class OpenToscaServerUserId implements Serializable {

		private static final long serialVersionUID = 1L;

		@ManyToOne
		@JoinColumn(name = "open_tosca_server_id")
		private OpenToscaServer openToscaServer;

		@ManyToOne
		@JoinColumn(name = "user_id")
		private User user;

		public OpenToscaServerUserId() {
		}

		public OpenToscaServerUserId(OpenToscaServer openToscaServer, User user) {
			this.openToscaServer = openToscaServer;
			this.user = user;
		}

		@Override
		public boolean equals(Object instance) {
			if (null == instance) {
				return false;
			}

			if (!(instance instanceof OpenToscaServerUserId)) {
				return false;
			}

			final OpenToscaServerUserId other = (OpenToscaServerUserId) instance;

			if (!(openToscaServer.getId().equals(other.getOpenToscaServer().getId()))) {
				return false;
			}

			if (!(user.getId().equals(other.getUser().getId()))) {
				return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			return this.openToscaServer.hashCode() ^ this.user.hashCode();
		}

		/**
		 * @return the User
		 */
		public User getUser() {
			return user;
		}

		/**
		 * @return the OpenToscaServer
		 */
		public OpenToscaServer getOpenToscaServer() {
			return openToscaServer;
		}

	}

}
