package org.opentosca.csarrepo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.service.ExportToWineryService;

@SuppressWarnings("serial")
@WebServlet(ExportToWineryServlet.PATH)
public class ExportToWineryServlet extends AbstractServlet {

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
			checkUserAuthentication(request, response);
		} catch (AuthenticationException e) {
			return;
		}

		long wineryServerId = Long.parseLong(request.getParameter(PARAM_WS_ID));
		long fileId = Long.parseLong(request.getParameter(PARAM_CSARFILE_ID));

		ExportToWineryService service = new ExportToWineryService(0L, wineryServerId, fileId);

		if (service.hasErrors()) {
			response.getWriter().write(service.getErrors().get(0));
		}

		this.redirect(request, response, CsarFileDetailsServlet.PATH.replace("*", "" + fileId));
	}

}
