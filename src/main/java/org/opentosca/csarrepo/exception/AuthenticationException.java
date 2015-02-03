/**
 * 
 */
package org.opentosca.csarrepo.exception;

/**
 * Provides an exception for failed authentications
 * 
 * @author Marcus Eisele (marcus.eisele@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class AuthenticationException extends Exception {

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(Throwable cause) {
		super(cause);
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

}
