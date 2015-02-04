package org.opentosca.csarrepo.service;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.model.repository.UserRepository;

/**
 *
 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
 */
public class LoadCheckedUserService extends AbstractService {

	private User user;

	/**
	 * Loads an user by given name.
	 * 
	 * @param name
	 */
	public LoadCheckedUserService(String name, String hashedPassword) {
		super(0);

		try {
			UserRepository userRepository = new UserRepository();
			User user = userRepository.getByName(name);
			if (null == user) {
				throw new PersistenceException("User does not exist!");
			}
			if (!user.getPassword().equals(hashedPassword)) {
				throw new PersistenceException("Password does not match!");
			}
			this.user = user;
		} catch (PersistenceException e) {
			AbstractServlet.addError(e.getMessage());
		}

	}

	/**
	 * 
	 * @return the user
	 */
	public User getResult() {
		super.logInvalidResultAccess("getResult");

		return this.user;
	}

}
