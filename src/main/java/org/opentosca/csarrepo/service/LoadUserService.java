package org.opentosca.csarrepo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.model.repository.UserRepository;

/**
 * 
 * @author Dennis Przytarski
 */
public class LoadUserService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(LoadUserService.class);
	User user;

	/**
	 * Loads an user by given name.
	 * 
	 * @param name
	 */
	public LoadUserService(String name) {
		super(0);

		UserRepository userRepository = new UserRepository();

		try {
			this.user = userRepository.getByName(name);
		} catch (PersistenceException e) {
			this.addError(e.getMessage());
			LOGGER.error(e);
		}
	}

	/**
	 * Loads an user by given id.
	 * 
	 * @param id
	 */
	public LoadUserService(long id) {
		super(0);

		UserRepository userRepository = new UserRepository();

		try {
			this.user = userRepository.getById(id);
		} catch (PersistenceException e) {
			this.addError(e.getMessage());
			LOGGER.error(e);
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
