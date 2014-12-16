package org.opentosca.csarrepo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractService {

	private static final Logger LOGGER = LogManager.getLogger();

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
	 * Adds an error to the error list
	 * 
	 * @param error
	 *            the new error
	 */
	protected void addError(String error) {
		this.errors.add(error);
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

	protected void logInvalidResultAccess(String methodName) {
		if (this.hasErrors()) {
			LOGGER.warn(this.getClass().getName() + "@" + methodName
					+ ": result accessed despite errors");
		}
	}
}
