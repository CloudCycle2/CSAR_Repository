package org.opentosca.csarrepo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
