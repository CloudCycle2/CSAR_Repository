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
import org.opentosca.csarrepo.service.UpdateWineryServerService;

/**
 * Servlet for creation of winery server
 */
@SuppressWarnings("serial")
@WebServlet(UpdateWineryServerServlet.PATH)
public class UpdateWineryServerServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(UpdateWineryServerServlet.class);
	private static final String PARAM_WINERY_SERVER_NAME = "wineryServerName";
	private static final String PARAM_WINERY_SERVER_URL = "wineryServerUrl";
	public static final String PATH = "/updatewineryserver/*";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateWineryServerServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(405, "Method Not Allowed");
	}

	/**
	 * @throws IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			User user = checkUserAuthentication(request, response);

			String wineryName = request.getParameter(PARAM_WINERY_SERVER_NAME);
			String wineryUrl = request.getParameter(PARAM_WINERY_SERVER_URL);

			// TODO: length-check
			String[] pathInfo = request.getPathInfo().split("/");
			// TODO: handle exception
			long wineryServerId = Long.parseLong(pathInfo[1]); // {id}

			UpdateWineryServerService service = new UpdateWineryServerService(user.getId(), wineryServerId, wineryName,
					wineryUrl);

			LOGGER.debug("Request to update winery server " + wineryServerId + " handeled by servlet");

			if (service.hasErrors()) {
				response.getWriter().write(service.getErrors().get(0));
				return;
			}

			this.redirect(request, response,
					WineryServerDetailsServlet.PATH.replaceFirst("\\*", Long.toString(wineryServerId)));
		} catch (AuthenticationException e) {
			return;
		}
	}

}
