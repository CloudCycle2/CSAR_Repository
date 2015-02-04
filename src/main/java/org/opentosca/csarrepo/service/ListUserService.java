package org.opentosca.csarrepo.service;

import java.util.List;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.model.repository.UserRepository;

/**
 * @author Dennis Przytarski
 */
public class ListUserService extends AbstractService {

	private final UserRepository userRepository = new UserRepository();
	private List<User> users = null;

	/**
	 * @param userId
	 */
	public ListUserService(long userId) {
		super(userId);

		try {
			this.users = userRepository.getAll();
		} catch (PersistenceException e) {
			this.addError(e.getMessage());
		}
	}

	/**
	 * @return List of users
	 */
	public List<User> getResult() {
		super.logInvalidResultAccess("getResult");

		return this.users;
	}

}
