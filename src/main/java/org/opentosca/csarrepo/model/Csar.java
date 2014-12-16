package org.opentosca.csarrepo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "csar")
	private List<CsarFile> csarFiles;

	public Csar() {
		this.csarFiles = new ArrayList<CsarFile>();
	}

	/**
	 * @return the id
	 */
	public long getId() {
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
	 * Returns all CSAR files of the current CSAR
	 * 
	 * @return the list of CSAR files
	 */
	public List<CsarFile> getCsarFiles() {
		return csarFiles;
	}
}
