package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.model.WineryServer;
import org.opentosca.csarrepo.service.ListOpenToscaServerService;
import org.opentosca.csarrepo.service.ListWineryServerService;
import org.opentosca.csarrepo.service.ShowCsarFileService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Implementation of the detail page for Csar files
 */
@SuppressWarnings("serial")
@WebServlet(CsarFileDetailsServlet.PATH)
public class CsarFileDetailsServlet extends AbstractServlet {

	private static final String TEMPLATE_NAME = "csarfiledetailsservlet.ftl";
	public static final String PATH = "/csarfile/*";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CsarFileDetailsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = checkUserAuthentication(request, response);
			Map<String, Object> root = getRoot(request);
			Template template = getTemplate(this.getServletContext(), TEMPLATE_NAME);

			// TODO: length-check
			String[] pathInfo = request.getPathInfo().split("/");
			// TODO: handle exception
			long csarFileId = Long.parseLong(pathInfo[1]); // {id}

			// TODO: add real UserID
			ShowCsarFileService showService = new ShowCsarFileService(0L, csarFileId);
			ListOpenToscaServerService listOTService = new ListOpenToscaServerService(0L);
			ListWineryServerService listWSService = new ListWineryServerService(0);

			AbstractServlet.addErrors(request, showService.getErrors());
			AbstractServlet.addErrors(request, listOTService.getErrors());
			AbstractServlet.addErrors(request, listWSService.getErrors());

			if (AbstractServlet.hasErrors(request)) {
				return;
			}

			List<OpenToscaServer> otInstances = listOTService.getResult();
			List<WineryServer> wineryServers = listWSService.getResult();
			CsarFile csarFile = showService.getResult();

			root.put("allOpentoscaServers", otInstances);
			root.put("wineryServers", wineryServers);
			// FIXME: use only OT instances related to the CsarFile and not all
			root.put("cloudInstances", csarFile.getCloudInstances());
			root.put("csarFile", csarFile);
			root.put("hashedFile", csarFile.getHashedFile());
			root.put("csar", csarFile.getCsar());
			root.put("title", String.format("%s @ %s", csarFile.getCsar().getName(), csarFile.getVersion()));
			template.process(root, response.getWriter());
		} catch (AuthenticationException e) {
			return;
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}

	}
}
