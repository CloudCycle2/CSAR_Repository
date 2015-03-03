package org.opentosca.csarrepo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.service.DeleteUserService;

/**
 * Implementation of the <code>DeleteUserServlet</code> servlet
 *
 * @author Thomas Kosch (mail@thomaskosch.com)
 *
 */
@SuppressWarnings("serial")
@WebServlet(DeleteUserServlet.PATH)
public class DeleteUserServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(DeleteUserServlet.class);
	public static final String PATH = "/deleteuser/*";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = checkUserAuthentication(request, response);
			String[] pathInfo;
			pathInfo = request.getPathInfo().split("/");
			long userIdToDelete = Long.parseLong(pathInfo[1]);
			DeleteUserService deleteUserService = new DeleteUserService(user.getId(), userIdToDelete);
			AbstractServlet.addErrors(request, deleteUserService.getErrors());
			if(!deleteUserService.hasErrors()) {
				AbstractServlet.addSuccess(request, "User deleted successfully");
			}
			this.redirect(request, response, ListUserServlet.PATH);
		} catch (AuthenticationException e) {
			return;
		} catch (Exception e) {
			LOGGER.error("Error while parsing URL parameters", e);
			AbstractServlet.addError(request, "Error while parsing URL parameters");
			this.redirect(request, response, DashboardServlet.PATH);
			return;
		}
	}

}
