package org.opentosca.csarrepo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * Hibernate annotated class for join table CsarOpenToscaServer
 * 
 * @author Thomas Kosch (mail@thomaskosch.com)
 *
 */
@Entity
@Table(name = "csar_open_tosca_server")
public class CsarOpenToscaServer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "csar_open_tosca_server_id")
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "csar_id")
	private Csar csarId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "open_tosca_server_id")
	private OpenToscaServer openToscaServerId;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return The Csar id
	 */
	public Csar getCsarId() {
		return csarId;
	}

	/**
	 * @param csarId
	 *            The Csar id
	 */
	public void setCsarId(Csar csarId) {
		this.csarId = csarId;
	}

	/**
	 * @return The OpenTosca server id
	 */
	public OpenToscaServer getOpenToscaServerId() {
		return openToscaServerId;
	}

	/**
	 * @param openToscaServerId
	 *            The OpenTosca server id
	 */
	public void setOpenToscaServerId(OpenToscaServer openToscaServerId) {
		this.openToscaServerId = openToscaServerId;
	}
}
