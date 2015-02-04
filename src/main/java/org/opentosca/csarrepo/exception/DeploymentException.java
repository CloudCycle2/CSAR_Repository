/**
 * 
 */
package org.opentosca.csarrepo.exception;

/**
 * Provides an exception for failed deployments
 * 
 * @author Marcus Eisele (marcus.eisele@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class DeploymentException extends Exception {

	public DeploymentException() {
		super();
	}

	public DeploymentException(String message) {
		super(message);
	}

	public DeploymentException(Throwable cause) {
		super(cause);
	}

	public DeploymentException(String message, Throwable cause) {
		super(message, cause);
	}

}
