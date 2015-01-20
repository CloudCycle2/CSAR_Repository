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
 * Hibernate annotated class for join table CsarWineryServer
 * 
 * @author Thomas Kosch (mail@thomaskosch.com)
 *
 */
@Entity
@Table(name = "csar_winery_server")
public class CsarWineryServer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "csar_winery_server_id")
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "csar_id")
	private Csar csarId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "winery_server_id")
	private WineryServer wineryServerId;

}
