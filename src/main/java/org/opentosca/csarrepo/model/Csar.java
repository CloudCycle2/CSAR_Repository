package org.opentosca.csarrepo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.opentosca.csarrepo.model.join.CsarUser;
import org.opentosca.csarrepo.model.join.CsarWineryServer;

/**
 * 
 * Hibernate Annotated class for Csar
 * 
 * @author eiselems (marcus.eisele@gmail.com), Thomas Kosch
 *         (mail@thomaskosch.com), Dennis Przytarski
 *
 */
@Entity
@Table(name = "csar")
public class Csar {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "csar_id")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "namespace")
	private String namespace;

	@Column(name = "service_template_id")
	private String serviceTemplateId;

	@OneToMany(mappedBy = "csar")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CsarFile> csarFiles = new ArrayList<CsarFile>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "csarUserId.csar")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CsarUser> csarUser = new ArrayList<CsarUser>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "csarWineryServerId.csar")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CsarWineryServer> csarWineryServer = new ArrayList<CsarWineryServer>();

	@ElementCollection
	@MapKeyColumn(name = "key_planID")
	@Column(name = "value_zipFileName")
	@CollectionTable(name = "csar_plans", joinColumns = @JoinColumn(name = "csar_id"))
	@LazyCollection(LazyCollectionOption.FALSE)
	// used to map the planID to the relating zip-filename
	private Map<String, String> plans = new HashMap<String, String>();

	public Csar() {
	}

	public Csar(Long id) {
		this.id = id;
	}

	/**
	 * This method maps an User instance to the corresponding Csar in the
	 * database
	 * 
	 * @param user
	 *            An User object instance
	 */
	public void addUser(User user) {
		CsarUser csarUser = new CsarUser(new CsarUser.CsarUserId(this, user));

		csarUser.setCsar(this);
		csarUser.setUser(user);

		this.csarUser.add(csarUser);
	}

	/**
	 * This method maps an WineryServer instance to the corresponding Csar in
	 * the database
	 * 
	 * @param wineryServer
	 *            A WineryServer object instance
	 */
	public void addWineryServer(WineryServer wineryServer) {
		CsarWineryServer csarWineryServer = new CsarWineryServer(new CsarWineryServer.CsarWineryServerId(this,
				wineryServer));

		csarWineryServer.setCsar(this);
		csarWineryServer.setWineryServer(wineryServer);

		this.csarWineryServer.add(csarWineryServer);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * @param namespace
	 *            the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * @return the service template id
	 */
	public String getServiceTemplateId() {
		return serviceTemplateId;
	}

	/**
	 * @param service_template_id
	 *            the service template id to set
	 */
	public void setServiceTemplateId(String service_template_id) {
		this.serviceTemplateId = service_template_id;
	}

	/**
	 * Returns all CSAR files of the current CSAR
	 * 
	 * @return the list of CSAR files
	 */
	public List<CsarFile> getCsarFiles() {
		return csarFiles;
	}

	/**
	 * @param csarFiles
	 *            List containing the correlation of the respective classes
	 */
	public void setCsarFiles(List<CsarFile> csarFiles) {
		this.csarFiles = csarFiles;
	}

	/**
	 * @return List containing the correlation of the respective classes
	 */
	public List<CsarUser> getCsarUser() {
		return csarUser;
	}

	/**
	 * @param csarUser
	 *            List containing the correlation of the respective classes
	 */
	public void setCsarUser(List<CsarUser> csarUser) {
		this.csarUser = csarUser;
	}

	/**
	 * @return List containing the correlation of the respective classes
	 */
	public List<CsarWineryServer> getCsarWineryServer() {
		return csarWineryServer;
	}

	/**
	 * @param csarWineryServer
	 *            List containing the correlation of the respective classes
	 */
	public void setCsarWineryServer(List<CsarWineryServer> csarWineryServer) {
		this.csarWineryServer = csarWineryServer;
	}

	/**
	 * Adds a reference entry for a plan (id to zipFileName)
	 * 
	 * @param planId
	 * @param planReference
	 */
	public void addPlan(String planId, String planReference) {
		this.plans.put(planId, planReference);
	}

	/**
	 * returns the mappings from planID to planReference (ID -> zipFileName)
	 * 
	 * @return Map
	 */
	public Map<String, String> getPlanReferences() {
		return this.plans;
	}

}
