package org.opentosca.csarrepo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.service.CreateWineryServerService;

/**
 * Servlet for creation of winery server
 */
@SuppressWarnings("serial")
@WebServlet(CreateWineryServerServlet.PATH)
public class CreateWineryServerServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(CreateWineryServerServlet.class);
	private static final String PARAM_WINERY_SERVER_NAME = "wineryServerName";
	private static final String PARAM_WINERY_SERVER_URL = "wineryServerUrl";
	public static final String PATH = "/createwinery";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateWineryServerServlet() {
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
			String wineryName = request.getParameter(PARAM_WINERY_SERVER_NAME);
			String wineryUrl = request.getParameter(PARAM_WINERY_SERVER_URL);

			CreateWineryServerService service = new CreateWineryServerService(0L, wineryName, wineryUrl);

			LOGGER.debug("Request to create winery server " + wineryName + " handeled by servlet");

			if (service.hasErrors()) {
				throw new ServletException("CreateCsarService has Errors: " + service.getErrors().get(0));
			}

			this.redirect(request, response, ListWineryServerServlet.PATH);
		} catch (ServletException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
