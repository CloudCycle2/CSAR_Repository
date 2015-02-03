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
import org.opentosca.csarrepo.service.DeleteWineryServerService;

@SuppressWarnings("serial")
@WebServlet(DeleteWineryServerServlet.PATH)
public class DeleteWineryServerServlet extends AbstractServlet {

	public final static String PATH = "/deletewineryserver/*";
	private static final Logger LOGGER = LogManager.getLogger(DeleteWineryServerServlet.class);

	public DeleteWineryServerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			checkUserAuthentication(request, response);
		} catch (AuthenticationException e) {
			return;
		}

		// TODO: length-check
		String[] pathInfo = request.getPathInfo().split("/");
		// TODO: handle exception
		long wineryServerId = Long.parseLong(pathInfo[1]); // {id}

		DeleteWineryServerService service = new DeleteWineryServerService(0, wineryServerId);

		if (service.hasErrors()) {
			LOGGER.error("deleting wineryServer failed with error" + service.getErrors().get(0));
			response.getWriter().print(service.getErrors().get(0));
		} else {
			this.redirect(request, response, ListWineryServerServlet.PATH);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		response.sendError(405, "Method Not Allowed");
	}
}
