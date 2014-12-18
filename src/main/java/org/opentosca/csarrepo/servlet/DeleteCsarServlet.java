package org.opentosca.csarrepo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.service.DeleteCsarService;

/**
 * 
 * @author Thomas Kosch (mail@thomaskosch.com)
 *
 */
@SuppressWarnings("serial")
@WebServlet("/deletecsar/*")
public class DeleteCsarServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(CreateCsarServlet.class);

	public DeleteCsarServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String[] pathInfo = request.getPathInfo().split("/");
		if (pathInfo[1] == null) {
			throw new ServletException("Error while parsing URL parameters");
		}
		long csarId = Long.parseLong(pathInfo[1]);
		DeleteCsarService deleteCsarService = new DeleteCsarService(0L, csarId);
		boolean result = deleteCsarService.getResult();
		if (result) {
			response.sendRedirect(getBasePath() + ListCsarServlet.PATH);
		} else {
			// TODO: Improve error handling
			throw new ServletException("Error while deleting CSAR with Id " + csarId + "with error: "
					+ deleteCsarService.getErrors().get(0));
		}

	}
}
