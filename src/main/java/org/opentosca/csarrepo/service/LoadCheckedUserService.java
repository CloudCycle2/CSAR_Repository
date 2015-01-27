package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.model.repository.UserRepository;

/**
 *
 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
 */
public class LoadCheckedUserService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(LoadCheckedUserService.class);
	User user;

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
				throw new PersistenceException(new Exception("User does not exist!"));
			}
			if (!user.getPassword().equals(hashedPassword)) {
				throw new PersistenceException(new Exception("Password does not match!"));
			}
			this.user = user;
		} catch (PersistenceException e) {
			this.addError(e.getMessage());
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
