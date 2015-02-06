package org.opentosca.csarrepo.model.join;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.OpenToscaServer;

/**
 * Join entity of the entity Csar and OpenToscaServer
 *
 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
 *
 */
@Entity
@Table(name = "csar_file_open_tosca_server")
public class CsarFileOpenToscaServer {

	@EmbeddedId
	private CsarFileOpenToscaServerId csarFileOpenToscaServerId;

	public CsarFileOpenToscaServer() {
	}

	public CsarFileOpenToscaServer(CsarFileOpenToscaServerId csarFileOpenToscaServerId) {
		this.csarFileOpenToscaServerId = csarFileOpenToscaServerId;
	}

	@ManyToOne
	@JoinColumn(name = "csar_file_id", insertable = false, updatable = false)
	private CsarFile csarFile;

	@ManyToOne
	@JoinColumn(name = "open_tosca_server_id", insertable = false, updatable = false)
	private OpenToscaServer openToscaServer;

	@Column(name = "location")
	private String location;

	/**
	 * @return the Csar file
	 */
	public CsarFile getCsarFile() {
		return csarFile;
	}

	/**
	 * @param csarFile
	 *            The Csar file
	 */
	public void setCsarFile(CsarFile csarFile) {
		this.csarFile = csarFile;
	}

	/**
	 * @return the OpenTosca server
	 */
	public OpenToscaServer getOpenToscaServer() {
		return openToscaServer;
	}

	/**
	 * @param openToscaServer
	 *            the OpenToscaServer
	 */
	public void setOpenToscaServer(OpenToscaServer openToscaServer) {
		this.openToscaServer = openToscaServer;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Embedded class for wrapping composite primary keys
	 *
	 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
	 *
	 */
	@Embeddable
	public static class CsarFileOpenToscaServerId implements Serializable {

		private static final long serialVersionUID = 1L;

		@ManyToOne
		@JoinColumn(name = "csar_file_id")
		private CsarFile csarFile;

		@ManyToOne
		@JoinColumn(name = "open_tosca_server_id")
		private OpenToscaServer openToscaServer;

		public CsarFileOpenToscaServerId() {
		}

		public CsarFileOpenToscaServerId(CsarFile csarFile, OpenToscaServer openToscaServer) {
			this.csarFile = csarFile;
			this.openToscaServer = openToscaServer;
		}

		@Override
		public boolean equals(Object instance) {
			if (null == instance) {
				return false;
			}

			if (!(instance instanceof CsarFileOpenToscaServerId)) {
				return false;
			}

			final CsarFileOpenToscaServerId other = (CsarFileOpenToscaServerId) instance;

			if (!(csarFile.getId().equals(other.getCsarFile().getId())))
				return false;

			if (!(openToscaServer.getId().equals(other.getOpenToscaServer().getId())))
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			return this.csarFile.hashCode() ^ this.openToscaServer.hashCode();
		}

		/**
		 * @return the Csar
		 */
		public CsarFile getCsarFile() {
			return csarFile;
		}

		/**
		 * @return the OpenToscaServer
		 */
		public OpenToscaServer getOpenToscaServer() {
			return openToscaServer;
		}

	}

}
