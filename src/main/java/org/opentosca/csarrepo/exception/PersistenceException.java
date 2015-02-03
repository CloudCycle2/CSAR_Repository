package org.opentosca.csarrepo.exception;

/**
 * Provides our own persistence exception.
 * 
 * @author Dennis Przytarski
 */
public class PersistenceException extends Exception {

	private static final long serialVersionUID = 1L;

	public PersistenceException() {
		super();
	}

	public PersistenceException(String message) {
		super(message);
	}

	/**
	 * This exception needs always the cause.
	 * 
	 * @param cause
	 */
	public PersistenceException(Throwable cause) {
		super(cause);
	}

	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

}
