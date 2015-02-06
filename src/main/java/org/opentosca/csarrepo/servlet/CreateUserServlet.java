package org.opentosca.csarrepo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.service.CreateUserService;

/**
 * Implementation of the creation of an user
 * 
 * @author Thomas Kosch, (mail@thomaskosch.com)
 *
 */
@SuppressWarnings("serial")
@WebServlet(CreateUserServlet.PATH)
public class CreateUserServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(CreateUserServlet.class);
	private static final String NAME = "name";
	private static final String PASSWORD = "password";
	private static final String MAIL = "mail";
	public static final String PATH = "/createuser";

	public CreateUserServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(405, "Method Not Allowed");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		try {
			this.checkUserAuthentication(request, response);

			String name = request.getParameter(NAME);
			String password = request.getParameter(PASSWORD);
			String mail = request.getParameter(MAIL);

			CreateUserService createUserService = new CreateUserService(name, mail, password);

			LOGGER.debug("Creating new User " + name + "...");
			if (createUserService.hasErrors()) {
				AbstractServlet.addErrors(request, createUserService.getErrors());
			}
			this.redirect(request, response, ListUserServlet.PATH);
		} catch (AuthenticationException e) {
			response.getWriter().print(e.getMessage());
			return;
		}
	}
}
