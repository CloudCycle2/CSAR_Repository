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
 * @author Thomas Kosch (mail@thomaskosch.com)
 */

@Entity
@Table(name = "file_system")
public class FileSystem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "file_system_id")
	private long id;

	@Column(name = "hash")
	private String hash;

	@Column(name = "file_name")
	private String fileName;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "file_system")
	private List<CsarFile> csarFiles;

	public FileSystem() {
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
	 * @param fileName
	 *            the filename to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 *
	 * @return the filename
	 */
	public String getFileName() {
		return fileName;
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
