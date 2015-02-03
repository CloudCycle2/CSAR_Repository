/**
 * 
 */
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
import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.model.repository.OpenToscaServerRepository;
import org.opentosca.csarrepo.util.ContainerApiClient;

/**
 * Servlet implementation class UploadCSARServlet
 */
@SuppressWarnings("serial")
@WebServlet(DeployCsarFileServlet.PATH)
public class DeployCsarFileServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(DeployCsarFileServlet.class);

	private static final String PARAM_CSARFILE_ID = "csarfileId";
	private static final String PARAM_OT_ID = "opentoscaId";
	public static final String PATH = "/deploycsarfile";

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
			checkUserAuthentication(request, response);

			int csarFileId = Integer.parseInt(request.getParameter(PARAM_CSARFILE_ID));
			int openToscaId = Integer.parseInt(request.getParameter(PARAM_OT_ID));

			OpenToscaServerRepository openToscaServerRepository = new OpenToscaServerRepository();
			CsarFileRepository csarFileRepo = new CsarFileRepository();

			OpenToscaServer openToscaServer = openToscaServerRepository.getbyId(openToscaId);
			ContainerApiClient containerApiClient = new ContainerApiClient(openToscaServer.getAddress());
			CsarFile csarFile = csarFileRepo.getbyId(csarFileId);
			boolean success = containerApiClient.uploadCSAR(csarFile);

			if (success) {
				response.getWriter().print("Upload seems to be succesful");
			} else {
				this.addError(request, "Deployment failed");
				return;
			}
		} catch (AuthenticationException e) {
			return;
		} catch (NumberFormatException | PersistenceException e) {
			this.addError(request, e.getMessage());
			this.redirect(request, response, DashboardServlet.PATH);
			LOGGER.error(e);
		}

	}
}
