package org.opentosca.csarrepo.util;

import java.io.InputStream;

/**
 * Wrapper object for WineryApiClient
 */
public class WineryCsarFileObject {

	private InputStream inputStream;
	private String filename;

	public WineryCsarFileObject(InputStream inputStream, String filename) {
		this.inputStream = inputStream;
		this.filename = filename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getFilename() {
		return filename;
	}

}
