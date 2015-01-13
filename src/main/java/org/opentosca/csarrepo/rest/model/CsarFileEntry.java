package org.opentosca.csarrepo.rest.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.HashedFile;

/**
 * Entry class for the csarrepo root
 *
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
@XmlRootElement(name = "csar_file")
public class CsarFileEntry extends AbstractEntry {

	private long id;

	private String name;

	private String hash;

	private String fileName;

	private long size;

	private Date uploadDate;

	private long csarFileVersion;

	protected CsarFileEntry() {
		super();
	}

	public CsarFileEntry(CsarFile csarFile, List<SimpleXLink> links) {
		super(links, null);
		this.id = csarFile.getId();
		this.name = csarFile.getName();
		HashedFile hashedFile = csarFile.getHashedFile();
		this.uploadDate = csarFile.getUploadDate();
		this.csarFileVersion = csarFile.getVersion();
		this.hash = hashedFile.getHash();
		this.fileName = hashedFile.getFileName();
		this.size = hashedFile.getSize();
	}

	@Override
	@XmlElement(name = "link")
	@XmlElementWrapper(name = "csar_files")
	public List<SimpleXLink> getChildren() {
		return super.getChildren();
	}

	@XmlElement(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "hash")
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@XmlElement(name = "filename")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@XmlElement(name = "size")
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@XmlElement(name = "uploaddate")
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@XmlElement(name = "version")
	public long getCsarFileVersion() {
		return csarFileVersion;
	}

	public void setCsarFileVersion(long csarFileVersion) {
		this.csarFileVersion = csarFileVersion;
	}
}
