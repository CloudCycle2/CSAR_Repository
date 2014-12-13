package org.opentosca.csarrepo.exception;

/**
 * Provides our own persistence exception.
 * 
 * @author Dennis Przytarski
 */
public class PersistenceException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * This exception needs always the cause.
	 * 
	 * @param cause
	 */
	public PersistenceException(Throwable cause) {
		super(cause);
	}

}
