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
			checkUserAuthentication(request, response);
		} catch (AuthenticationException e) {
			return;
		}

		String[] pathInfo;
		long csarId;
		try {
			pathInfo = request.getPathInfo().split("/");
			csarId = Long.parseLong(pathInfo[1]);
		} catch (Exception e) {
			LOGGER.error("Error while parsing URL parameters", e);
			throw new ServletException("Error while parsing URL parameters");
		}
		DeleteCsarService deleteCsarService = new DeleteCsarService(0L, csarId);
		boolean result = deleteCsarService.getResult();
		if (result) {
			this.redirect(request, response, ListCsarServlet.PATH);
		} else {
			// TODO: Improve error handling
			throw new ServletException("Error while deleting CSAR with Id " + csarId + "with error: "
					+ deleteCsarService.getErrors().get(0));
		}

	}
}
