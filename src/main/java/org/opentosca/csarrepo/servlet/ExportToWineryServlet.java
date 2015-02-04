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
import org.opentosca.csarrepo.service.ExportToWineryService;

@SuppressWarnings("serial")
@WebServlet(ExportToWineryServlet.PATH)
public class ExportToWineryServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(ExportToWineryServlet.class);

	private static final String PARAM_CSARFILE_ID = "csarfileId";
	private static final String PARAM_WS_ID = "wineryId";
	public static final String PATH = "/exporttowinery";

	public ExportToWineryServlet() {
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
			User user = checkUserAuthentication(request, response);
			long wineryServerId = Long.parseLong(request.getParameter(PARAM_WS_ID));
			long fileId = Long.parseLong(request.getParameter(PARAM_CSARFILE_ID));

			ExportToWineryService service = new ExportToWineryService(user.getId(), wineryServerId, fileId);

			if (service.hasErrors()) {
				response.getWriter().write(service.getErrors().get(0));
			}

			this.redirect(request, response, CsarFileDetailsServlet.PATH.replace("*", "" + fileId));
		} catch (AuthenticationException e) {
			return;
		} catch (Exception e) {
			AbstractServlet.addError(request, e.getMessage());
			this.redirect(request, response, DashboardServlet.PATH);
			LOGGER.error(e);
		}

	}

}
