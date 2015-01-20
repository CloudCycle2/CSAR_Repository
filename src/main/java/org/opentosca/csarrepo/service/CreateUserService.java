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
public class CreateUserService extends AbstractService {

	private static final Logger LOGGER = LogManager.getLogger(CreateUserService.class);
	long userId;

	/**
	 * Creates a new user.
	 * 
	 * @param name
	 * @param mail
	 * @param password
	 */
	public CreateUserService(String name, String mail, String password) {
		super(0);

		UserRepository userRepository = new UserRepository();

		try {
			User user = new User();
			user.setName(name);
			user.setMail(mail);
			user.setPassword(password); // TODO: hash password
			userId = userRepository.save(user);
		} catch (PersistenceException e) {
			this.addError(e.getMessage());
			LOGGER.error(e);
		}
	}

	/**
	 * 
	 * @return user id of the created user
	 */
	public long getResult() {
		super.logInvalidResultAccess("getResult");

		return this.userId;
	}

}
