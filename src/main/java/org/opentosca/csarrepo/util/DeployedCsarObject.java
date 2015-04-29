package org.opentosca.csarrepo.util;

/**
 * Wrapper object for LivedataOpenToscaCsarService
 */
public class DeployedCsarObject {

	private long id;
	private String title;

	public DeployedCsarObject(long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

}
