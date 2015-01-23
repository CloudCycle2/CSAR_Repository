package org.opentosca.csarrepo.model.join;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.OpenToscaServer;

/**
 * ] Join entity of the entity Csar and OpenToscaServer
 *
 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
 *
 */
@Entity
@Table(name = "csar_open_tosca_server")
public class CsarOpenToscaServer {

	@EmbeddedId
	private CsarOpenToscaServerId csarOpenToscaServerId;

	public CsarOpenToscaServer() {
	}

	public CsarOpenToscaServer(CsarOpenToscaServerId csarOpenToscaServerId) {
		this.csarOpenToscaServerId = csarOpenToscaServerId;
	}

	@ManyToOne
	@JoinColumn(name = "csar_id", insertable = false, updatable = false)
	private Csar csar;

	@ManyToOne
	@JoinColumn(name = "open_tosca_server_id", insertable = false, updatable = false)
	private OpenToscaServer openToscaServer;

	/**
	 * @param csar
	 *            The Csar
	 */
	public void setCsar(Csar csar) {
		this.csar = csar;
	}

	/**
	 * @param openToscaServer
	 *            the OpenToscaServer
	 */
	public void setOpenToscaServer(OpenToscaServer openToscaServer) {
		this.openToscaServer = openToscaServer;
	}

	/**
	 * Embedded class for wrapping composite primary keys
	 *
	 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
	 *
	 */
	@Embeddable
	public static class CsarOpenToscaServerId implements Serializable {

		private static final long serialVersionUID = 1L;

		@ManyToOne
		@JoinColumn(name = "csar_id")
		private Csar csar;

		@ManyToOne
		@JoinColumn(name = "open_tosca_server_id")
		private OpenToscaServer openToscaServer;

		public CsarOpenToscaServerId() {
		}

		public CsarOpenToscaServerId(Csar csar, OpenToscaServer openToscaServer) {
			this.csar = csar;
			this.openToscaServer = openToscaServer;
		}

		@Override
		public boolean equals(Object instance) {
			if (null == instance) {
				return false;
			}

			if (!(instance instanceof CsarOpenToscaServerId)) {
				return false;
			}

			final CsarOpenToscaServerId other = (CsarOpenToscaServerId) instance;

			if (!(csar.getId().equals(other.getCsar().getId())))
				return false;

			if (!(openToscaServer.getId().equals(other.getOpenToscaServer().getId())))
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			return this.csar.hashCode() ^ this.openToscaServer.hashCode();
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
		public OpenToscaServer getOpenToscaServer() {
			return openToscaServer;
		}

	}

}
