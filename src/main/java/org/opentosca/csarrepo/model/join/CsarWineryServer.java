package org.opentosca.csarrepo.model.join;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.WineryServer;

/**
 * 
 * Hibernate annotated class for join table CsarWineryServer
 * 
 * @author Thomas Kosch (mail@thomaskosch.com)
 *
 */
@Entity
@Table(name = "csar_winery_server")
public class CsarWineryServer {

	@EmbeddedId
	private CsarWineryServerId csarWineryServerId;

	public CsarWineryServer() {
	}

	public CsarWineryServer(CsarWineryServerId csarWineryServerId) {
		this.csarWineryServerId = csarWineryServerId;
	}

	@ManyToOne
	@JoinColumn(name = "csar_id", insertable = false, updatable = false)
	private Csar csar;

	@ManyToOne
	@JoinColumn(name = "winery_server_id", insertable = false, updatable = false)
	private WineryServer wineryServer;

	/**
	 * @param csar
	 *            The Csar to set
	 */
	public void setCsar(Csar csar) {
		this.csar = csar;
	}

	/**
	 * @param wineryServer
	 *            The Winery server to set
	 */
	public void setWineryServer(WineryServer wineryServer) {
		this.wineryServer = wineryServer;
	}

	/**
	 * Embedded class for wrapping composite primary keys
	 *
	 * @author Thomas Kosch (mail@thomaskosch.com)
	 *
	 */
	@Embeddable
	public static class CsarWineryServerId implements Serializable {

		private static final long serialVersionUID = 1L;

		@ManyToOne
		@JoinColumn(name = "csar_id")
		private Csar csar;

		@ManyToOne
		@JoinColumn(name = "winery_server_id")
		private WineryServer wineryServer;

		public CsarWineryServerId() {
		}

		public CsarWineryServerId(Csar csar, WineryServer wineryServer) {
			this.csar = csar;
			this.wineryServer = wineryServer;
		}

		@Override
		public boolean equals(Object instance) {
			if (null == instance) {
				return false;
			}

			if (!(instance instanceof CsarWineryServerId)) {
				return false;
			}

			final CsarWineryServerId other = (CsarWineryServerId) instance;

			if (!(csar.getId().equals(other.getCsar().getId())))
				return false;

			if (!(wineryServer.getId().equals(other.getWineryServer().getId())))
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			return this.csar.hashCode() ^ this.wineryServer.hashCode();
		}

		/**
		 * @return The Csar
		 */
		public Csar getCsar() {
			return csar;
		}

		/**
		 * @return The Winery server
		 */
		public WineryServer getWineryServer() {
			return wineryServer;
		}
	}
}
