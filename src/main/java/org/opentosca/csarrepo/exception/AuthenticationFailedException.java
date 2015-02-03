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
public class AuthenticationFailedException extends Exception {

	public AuthenticationFailedException() {
		super();
	}

	public AuthenticationFailedException(String message) {
		super(message);
	}

	public AuthenticationFailedException(Throwable cause) {
		super(cause);
	}

	public AuthenticationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

}
