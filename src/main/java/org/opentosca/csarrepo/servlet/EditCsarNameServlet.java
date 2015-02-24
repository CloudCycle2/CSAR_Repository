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
import org.opentosca.csarrepo.service.EditCsarNameService;

/**
 * Implementation which enables renaming selected Csars
 * 
 * @author Thomas Kosch (mail@thomaskosch.com), Dennis Przytarski
 *
 */

@SuppressWarnings("serial")
@WebServlet(EditCsarNameServlet.PATH)
public class EditCsarNameServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(EditCsarNameServlet.class);
	public static final String PATH = "/editcsarname";

	private static final String CSAR_ID = "csarId";
	private static final String NAME = "name";

	public EditCsarNameServlet() {
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
			User user = this.checkUserAuthentication(request, response);

			String csarId = request.getParameter(CSAR_ID);
			String name = request.getParameter(NAME);

			EditCsarNameService editCsarNameService = new EditCsarNameService(user.getId(), Long.parseLong(csarId),
					name);

			if (editCsarNameService.hasErrors()) {
				AbstractServlet.addErrors(request, editCsarNameService.getErrors());
			}
			this.redirect(request, response, CsarDetailsServlet.PATH.replace("*", csarId));

		} catch (AuthenticationException e) {
			response.getWriter().print(e.getMessage());
			LOGGER.error(e);
			return;
		}

	}
}
