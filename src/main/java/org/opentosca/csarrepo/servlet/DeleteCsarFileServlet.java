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
import org.opentosca.csarrepo.service.DeleteCsarFileService;

/**
 * 
 * @author Thomas Kosch (mail@thomaskosch.com)
 *
 */

@SuppressWarnings("serial")
@WebServlet(DeleteCsarFileServlet.PATH)
public class DeleteCsarFileServlet extends AbstractServlet {

	private final static String PARAM_CSAR_FILE_ID = "csarfileid";
	public final static String PATH = "/deletecsarfile";
	private static final Logger LOGGER = LogManager.getLogger(DeleteCsarFileServlet.class);

	public DeleteCsarFileServlet() {
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
		try {
			User user = checkUserAuthentication(request, response);
			String csarFileId = request.getParameter(PARAM_CSAR_FILE_ID);
			DeleteCsarFileService deleteCsarFileService = new DeleteCsarFileService(user.getId(),
					Long.parseLong(csarFileId));
			if (deleteCsarFileService.hasErrors()) {
				throw new ServletException("Error while initializing deleteCsarFileService");
			}
			boolean result = deleteCsarFileService.getResult();
			if (result) {
				this.redirect(request, response,
						CsarDetailsServlet.PATH.replace("*", String.valueOf(deleteCsarFileService.getCsar().getId())));
			}
		} catch (AuthenticationException e) {
			return;
		} catch (Exception e) {
			LOGGER.error("Error while deleting Csar file with Id: " + PARAM_CSAR_FILE_ID, e);
			response.getWriter().print(e.getMessage());
		}

	}
}
