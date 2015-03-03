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
import org.opentosca.csarrepo.service.ImportCsarFromWineryService;

/**
 * Servlet for creation of winery server
 */
@SuppressWarnings("serial")
@WebServlet(ImportCsarFromWineryServlet.PATH)
public class ImportCsarFromWineryServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(ImportCsarFromWineryServlet.class);

	private static final String PARAM_WINERY_SERVER_ID = "wineryId";
	private static final String PARAM_SERVICETEMPLATE = "servicetemplate";
	public static final String PATH = "/importcsarfromwinery";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImportCsarFromWineryServlet() {
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

			long wineryId = 0;
			try {
				wineryId = Long.parseLong(request.getParameter(PARAM_WINERY_SERVER_ID));
			} catch(NumberFormatException e) {
				// TODO handle invalid wineryid
			}
			String servicetemplate = request.getParameter(PARAM_SERVICETEMPLATE);

			ImportCsarFromWineryService service;
				service = new ImportCsarFromWineryService(user.getId(), wineryId, servicetemplate);
			
			if (service.hasErrors()) {
				AbstractServlet.addErrors(request, service.getErrors());
				
				// redirect to winery site
				this.redirect(request, response, WineryServerDetailsServlet.PATH.replace("*", "" + wineryId));
					
				return;
			}

			// everything went right --> redirec to the csarfile
			this.redirect(request, response, CsarFileDetailsServlet.PATH.replace("*", "" + service.getCsarFile().getId()));
		} catch (AuthenticationException e) {
			return;
		}
	}
}
