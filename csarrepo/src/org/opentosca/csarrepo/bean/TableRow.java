package org.opentosca.csarrepo.bean;

import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.CsarFile;

public class TableRow {

	private String name;
	private String uploaded;
	private String size;
	private boolean download;

	public TableRow(Csar csar) {
		this.name = csar.getName();
		this.uploaded = "";
		this.size = "";
		this.download = false;
	}

	public TableRow(CsarFile csarFile) {
		this.name = csarFile.getVersion();
		this.uploaded = csarFile.getUploadDate().toString();
		this.size = String.valueOf(csarFile.getSize());
		this.download = true;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the uploaded
	 */
	public String getUploaded() {
		return uploaded;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @return the download
	 */
	public boolean getDownload() {
		return download;
	}

}
