package org.opentosca.csarrepo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.service.DeleteCsarService;

/**
 * 
 * @author Thomas Kosch (mail@thomaskosch.com)
 *
 */
@SuppressWarnings("serial")
@WebServlet(DeleteCsarServlet.PATH)
public class DeleteCsarServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(DeleteCsarServlet.class);
	public static final String PATH = "/deletecsar/*";

	public DeleteCsarServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		try {
			User user = checkUserAuthentication(request, response);
			String[] pathInfo;
			long csarId;
			pathInfo = request.getPathInfo().split("/");
			csarId = Long.parseLong(pathInfo[1]);
			DeleteCsarService deleteCsarService = new DeleteCsarService(user.getId(), csarId);
			if(deleteCsarService.hasErrors()) {
				AbstractServlet.addErrors(request, deleteCsarService.getErrors());
				this.redirect(request, response, CsarDetailsServlet.PATH.replace("*", "" + csarId));
				
				return;
			}
			AbstractServlet.addSuccess(request, "CSAR deleted successfully");
			this.redirect(request, response, ListCsarServlet.PATH);
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
