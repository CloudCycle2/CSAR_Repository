package org.opentosca.csarrepo.service;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.repository.UserRepository;

/**
 * Implementation of a service which deletes an user
 *
 * @author Thomas Kosch
 *
 */
public class DeleteUserService extends AbstractService {

	private final UserRepository userRepository = new UserRepository();

	public DeleteUserService(long userId, long userIdToDelete) {
		super(userId);
		try {
			userRepository.delete(userRepository.getById(userIdToDelete));
		} catch (PersistenceException e) {
			this.addError(e.getMessage());
		}
	}

	public boolean getResult() {
		if (this.hasErrors()) {
			this.logInvalidResultAccess("getResult");
			return false;
		} else {
			return true;
		}
	}
}
