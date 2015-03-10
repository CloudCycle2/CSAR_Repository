package org.opentosca.csarrepo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author Thomas Kosch (mail@thomaskosch.com)
 */

@Entity
@Table(name = "hashed_file")
public class HashedFile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hashed_file_id")
	private long id;

	@Column(name = "hash")
	private String hash;

	@Column(name = "filename")
	private String filename;

	@Column(name = "size")
	private long size;

	@OneToMany(mappedBy = "hashedFile")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<CsarFile> csarFiles;

	@OneToMany(mappedBy = "csar")
	@LazyCollection(LazyCollectionOption.FALSE)
	@MapKey(name = "name")
	private Map<String, CsarPlan> csarPlans = new HashMap<String, CsarPlan>();

	public HashedFile() {
		this.csarFiles = new ArrayList<CsarFile>();
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * @param hash
	 *            the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 *
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(UUID filename) {
		this.filename = String.valueOf(filename);
	}

	/**
	 *
	 * @return the filename
	 */
	public UUID getFilename() {
		return UUID.fromString(filename);
	}

	/**
	 *
	 * @param size
	 *            the size of the hashed file
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 *
	 * @return the size of the file
	 */
	public long getSize() {
		return size;
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
	 * Adds a plan entry for a plan
	 * 
	 * @param planId
	 * @param planReference
	 */
	public void addPlan(String planId, CsarPlan plan) {
		this.csarPlans.put(planId, plan);
	}

	/**
	 * returns the mappings from planID to planReference (ID -> zipFileName)
	 * 
	 * @return Map
	 */
	public Map<String, CsarPlan> getPlans() {
		return this.csarPlans;
	}

}
