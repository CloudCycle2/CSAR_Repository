/**
 * 
 */
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
import org.opentosca.csarrepo.service.UndeployCsarFileOpenToscaService;

/**
 * Servlet implementation class UploadCSARServlet
 */
@SuppressWarnings("serial")
@WebServlet(UndeployCsarFileOpenToscaServlet.PATH)
public class UndeployCsarFileOpenToscaServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(UndeployCsarFileOpenToscaServlet.class);

	public static final String PARAM_CSARFILE_ID = "csarfileId";
	public static final String PARAM_OT_ID = "opentoscaId";
	public static final String PATH = "/undeploycsarfile";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = checkUserAuthentication(request, response);

			int openToscaId = Integer.parseInt(request.getParameter(PARAM_OT_ID));
			int csarFileId = Integer.parseInt(request.getParameter(PARAM_CSARFILE_ID));

			UndeployCsarFileOpenToscaService undeployService = new UndeployCsarFileOpenToscaService(user.getId(),
					openToscaId, csarFileId);
			AbstractServlet.addErrors(request, undeployService.getErrors());
			this.redirect(request, response, CsarFileDetailsServlet.PATH.replace("*", "" + csarFileId));

		} catch (AuthenticationException e) {
			return;
		} catch (NumberFormatException e) {
			AbstractServlet.addError(request, e.getMessage());
			this.redirect(request, response, DashboardServlet.PATH);
			LOGGER.error(e);
		}

	}
}
