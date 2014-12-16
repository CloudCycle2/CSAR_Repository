package org.opentosca.csarrepo.model;

import java.util.Date;

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
 * Hibernate class for entity CSARFile
 *
 * @author Thomas Kosch (mail@thomaskosch.com), Dennis Przytarski
 */

@Entity
@Table(name = "csar_file")
public class CsarFile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id")
	private Csar csar;

	@Column(name = "version")
	private String version;

	@Column(name = "upload_date")
	private Date uploadDate;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id")
	private HashedFile hashedFile;

	/**
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
	 * @param csar
	 *            the csar to set
	 */
	public void setCsar(Csar csar) {
		this.csar = csar;
	}

	/**
	 * Get CSAR of the current CSAR file
	 *
	 * @return the CSAR
	 */
	public Csar getCsar() {
		return csar;
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

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the hashedFile
	 */
	public HashedFile getHashedFile() {
		return hashedFile;
	}

	/**
	 * @param hashedFile
	 *            the hashedFile to set
	 */
	public void setHashedFile(HashedFile hashedFile) {
		this.hashedFile = hashedFile;
	}
}
