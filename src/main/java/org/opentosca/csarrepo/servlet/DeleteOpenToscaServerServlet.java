package org.opentosca.csarrepo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.service.DeleteOpenToscaServerService;

/**
 * Servlet for deleting OpenToscaServers
 * 
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
@SuppressWarnings("serial")
@WebServlet(DeleteOpenToscaServerServlet.PATH)
public class DeleteOpenToscaServerServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(DeleteOpenToscaServerServlet.class);
	public static final String PATH = "/deleteopentoscaserver/*";

	public DeleteOpenToscaServerServlet() {
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
		checkUserAuthentication(request, response);

		String[] pathInfo;
		long otServerId;
		try {
			pathInfo = request.getPathInfo().split("/");
			otServerId = Long.parseLong(pathInfo[1]);
		} catch (Exception e) {
			LOGGER.error("Error while parsing URL parameters", e);
			throw new ServletException("Error while parsing URL parameters");
		}
		// TODO: use real user
		DeleteOpenToscaServerService deleteOtServerService = new DeleteOpenToscaServerService(0L, otServerId);
		boolean result = deleteOtServerService.getResult();
		if (result) {
			this.redirect(request, response, ListOpenToscaServerServlet.PATH);
		} else {
			// TODO: Improve error handling
			throw new ServletException("Error while deleting OpenTOSCA Server with Id " + otServerId + "with error: "
					+ deleteOtServerService.getErrors().get(0));
		}

	}
}
