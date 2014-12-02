package org.opentosca.csarrepo.service;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractService {

	private long userId;
	private List<String> errors;

	/**
	 * Constructor of an abstract service
	 * 
	 * @param userId
	 *            the id of the user
	 */
	public AbstractService(long userId) {
		this.userId = userId;
		this.errors = new ArrayList<String>();
	}

	/**
	 * @return whether this service has errors or not
	 */
	public boolean hasErrors() {
		return this.errors.size() > 0;
	}

	/**
	 * @return the list of errors
	 */
	public List<String> getErrors() {
		return this.errors;
	}
}
