package org.opentosca.csarrepo.model.join;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
