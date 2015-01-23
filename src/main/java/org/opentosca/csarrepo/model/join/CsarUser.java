package org.opentosca.csarrepo.model.join;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.User;

/**
 * 
 * Hibernate annotated class for join table CsarUser
 * 
 * @author Thomas Kosch (mail@thomaskosch.com)
 *
 */

@Entity
@Table(name = "csar_user")
public class CsarUser {

	@EmbeddedId
	private CsarUserId csarUserId;

	public CsarUser() {
	}

	public CsarUser(CsarUserId csarUserId) {
		this.csarUserId = csarUserId;
	}

	@ManyToOne
	@JoinColumn(name = "csar_id", insertable = false, updatable = false)
	private Csar csar;

	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	/**
	 * @param csar
	 *            The Csar to set
	 */
	public void setCsar(Csar csar) {
		this.csar = csar;
	}

	/**
	 * @param user
	 *            The User to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Embedded class for wrapping composite primary keys
	 *
	 * @author Thomas Kosch (mail@thomaskosch.com)
	 *
	 */
	@Embeddable
	public static class CsarUserId implements Serializable {

		private static final long serialVersionUID = 1L;

		@ManyToOne
		@JoinColumn(name = "csar_id")
		private Csar csar;

		@ManyToOne
		@JoinColumn(name = "user_id")
		private User user;

		public CsarUserId() {
		}

		public CsarUserId(Csar csar, User user) {
			this.csar = csar;
			this.user = user;
		}

		@Override
		public boolean equals(Object instance) {
			if (null == instance) {
				return false;
			}

			if (!(instance instanceof CsarUserId)) {
				return false;
			}

			final CsarUserId other = (CsarUserId) instance;

			if (!(csar.getId().equals(other.getCsar().getId())))
				return false;

			if (!(user.getId().equals(other.getUser().getId())))
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			return this.csar.hashCode() ^ this.user.hashCode();
		}

		/**
		 * @return the Csar
		 */
		public Csar getCsar() {
			return csar;
		}

		/**
		 * @return the OpenToscaServer
		 */
		public User getUser() {
			return user;
		}

	}

}
