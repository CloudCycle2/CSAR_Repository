package org.opentosca.csarrepo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Hibernate class for entity CSARFile
 * 
 * @author Thomas Kosch (mail@thomaskosch.com), Dennis Przytarski
 *
 */

@Entity
@Table(name = "csar_file")
public class CsarFile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "size")
	private long size;

	@Column(name = "hash")
	private String hash;

	@Column(name = "version")
	private String version;

	@Column(name = "path")
	private String path;

	@Column(name = "uploadDate")
	private Date uploadDate;

	/**
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get the size of a CSAR
	 * 
	 * @return size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * Sets the size of a CSAR
	 * 
	 * @param size
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * Get the hash of a CSAR
	 * 
	 * @return hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * Sets the hash of a CSAR
	 * 
	 * @param hash
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * Gets the version of a CSAR
	 * 
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version of a CSAR
	 * 
	 * @param version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the path of a CSAR
	 * 
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path of a CSAR
	 * 
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Gets the upload date of a CSAR
	 * 
	 * @return date of upload
	 */
	public Date getUploadDate() {
		return uploadDate;
	}

	/**
	 * Sets the upload date
	 * 
	 * @param uploadDate
	 */
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
}
