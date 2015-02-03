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
			// TODO: check if user is allowed to do so
			String csarFileID = request.getParameter(PARAM_CSARFILE_ID);
			String opentoscaID = request.getParameter(PARAM_OT_ID);

			int cfId = Integer.parseInt(csarFileID);
			int otId = Integer.parseInt(opentoscaID);
			OpenToscaServerRepository otsr = new OpenToscaServerRepository();
			OpenToscaServer otServer = otsr.getbyId(otId);

			ContainerApiClient containerApiClient = new ContainerApiClient(otServer.getAddress());
			CsarFileRepository csarFileRepo = new CsarFileRepository();
			CsarFile csarFile = csarFileRepo.getbyId(cfId);
			containerApiClient.uploadCSAR(csarFile);
			// TODO: better handling of response
			response.getWriter().print("Upload seems to be succesful");
		} catch (AuthenticationException e) {
			return;
		} catch (NumberFormatException e) {
			response.getWriter().print("Error: handling ids" + e.getMessage());
		} catch (PersistenceException e) {
			response.getWriter().print("Error: retrieving data by ids" + e.getMessage());
		}
	}
}
