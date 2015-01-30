package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.service.ListOpenToscaServerService;
import org.opentosca.csarrepo.service.ShowCsarFileService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class HelloWorldServlet
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
		checkUserAuthentication(request, response);

		try {
			Map<String, Object> root = getRoot();
			Template template = getTemplate(this.getServletContext(), TEMPLATE_NAME);

			// TODO: length-check
			String[] pathInfo = request.getPathInfo().split("/");
			// TODO: handle exception
			long csarFileId = Long.parseLong(pathInfo[1]); // {id}

			// TODO: add real UserID
			ShowCsarFileService showService = new ShowCsarFileService(0L, csarFileId);

			if (showService.hasErrors()) {
				// FIXME, get all errors - not only first
				throw new ServletException("ShowCsarFileService has errors:" + showService.getErrors().get(0));
			}

			ListOpenToscaServerService listOTService = new ListOpenToscaServerService(0L);
			if (listOTService.hasErrors()) {
				// FIXME, get all errors - not only first
				throw new ServletException("ListOpenToscaServerService has errors:" + listOTService.getErrors().get(0));
			}
			List<OpenToscaServer> otInstances = listOTService.getResult();
			root.put("allOpentoscaServers", otInstances);
			CsarFile csarFile = showService.getResult();
			// FIXME: use only OT instances related to the CsarFile and not all
			// FIXME: Implement iterator over all CsarFileOpenToscaServer in the
			// template!
			root.put("cloudInstances", csarFile.getCsarFileOpenToscaServer().get(0).getOpenToscaServer()
					.getCloudInstances());
			root.put("csarFile", csarFile);
			root.put("hashedFile", csarFile.getHashedFile());
			root.put("csar", csarFile.getCsar());
			root.put("title", String.format("%s @ %s", csarFile.getCsar().getName(), csarFile.getVersion()));
			template.process(root, response.getWriter());
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
