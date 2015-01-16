package org.opentosca.csarrepo.util;

import java.io.File;

/**
 * Wrapper object for DownloadCsarFileService
 * 
 * @author Dennis Przytarski
 */
public class DownloadCsarFileObject {

	private File file;
	private String filename;

	public DownloadCsarFileObject(File file, String filename) {
		this.file = file;
		this.filename = filename;
	}

	public File getFile() {
		return this.file;
	}

	public String getFilename() {
		return this.filename;
	}

}
