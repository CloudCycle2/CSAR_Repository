package org.opentosca.csarrepo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.service.UpdateOpenToscaServerService;

@SuppressWarnings("serial")
@WebServlet(UpdateOpenToscaServerServlet.PATH)
public class UpdateOpenToscaServerServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(UpdateOpenToscaServerServlet.class);
	private static final String PARAM_OPEN_TOSCA_SERVER_NAME = "openToscaServerName";
	private static final String PARAM_OPEN_TOSCA_SERVER_URL = "address";
	private static final String PARAM_OPEN_TOSCA_SERVER_ID = "openToscaServerId";
	public static final String PATH = "/updateopentoscaserver";

	public UpdateOpenToscaServerServlet() {
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
			checkUserAuthentication(request, response);
		} catch (AuthenticationException e) {
			return;
		}

		Long openToscaServerId = Long.parseLong(request.getParameter(PARAM_OPEN_TOSCA_SERVER_ID));
		String openToscaServerName = request.getParameter(PARAM_OPEN_TOSCA_SERVER_NAME);
		String OpenToscaServerAddress = request.getParameter(PARAM_OPEN_TOSCA_SERVER_URL);

		UpdateOpenToscaServerService service = new UpdateOpenToscaServerService(0L, openToscaServerId,
				openToscaServerName, OpenToscaServerAddress);

		LOGGER.debug("Request to update open tosca server " + openToscaServerId + " handeled by servlet");

		if (service.hasErrors()) {
			response.getWriter().write(service.getErrors().get(0));
			return;
		}

		this.redirect(request, response,
				OpenToscaServerDetailsServlet.PATH.replaceFirst("\\*", Long.toString(openToscaServerId)));

	}
}
