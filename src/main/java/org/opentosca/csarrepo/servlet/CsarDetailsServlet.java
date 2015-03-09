package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.service.ListWineryServerService;
import org.opentosca.csarrepo.service.ShowCsarService;
import org.opentosca.csarrepo.util.StringUtils;

import freemarker.template.Template;

/**
 * Servlet implementation class HelloWorldServlet
 */
@SuppressWarnings("serial")
@WebServlet(CsarDetailsServlet.PATH)
public class CsarDetailsServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(CsarDetailsServlet.class);

	private static final String TEMPLATE_NAME = "csarDetailsServlet.ftl";
	public static final String PATH = "/csar/*";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CsarDetailsServlet() {
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
			long csarId = StringUtils.getURLParameter(request.getPathInfo());

			ShowCsarService showService = new ShowCsarService(user.getId(), csarId);
			if (showService.hasErrors()) {
				AbstractServlet.addErrors(request, showService.getErrors());
				this.redirect(request, response, ListCsarServlet.PATH);
				return;
			}
			
			ListWineryServerService wineryList = new ListWineryServerService(user.getId());
			if(wineryList.hasErrors()) {
				AbstractServlet.addErrors(request, wineryList.getErrors());
			}
			
			String namespace = ""; 
			String templateId = "";
			if(showService.getResult().getNamespace() != null) {
				namespace = showService.getResult().getNamespace() + "/";
			}
			
			if(showService.getResult().getServiceTemplateId() != null) {
				templateId = showService.getResult().getServiceTemplateId() + "/";
			}
			
			String completeTemplateId = namespace + templateId;
			
			Csar result = showService.getResult();

			root.put("csar", result);
			root.put("csarFiles", result.getCsarFiles());
			root.put("title", String.format("%s: %s", result.getId(), result.getName()));
			root.put("wineryServers", wineryList.getResult());
			root.put("serviceTemplate", completeTemplateId);
			template.process(root, response.getWriter());
		} catch (AuthenticationException e) {
			return;
		} catch (Exception e) {
			AbstractServlet.addError(request, e.getMessage());
			this.redirect(request, response, DashboardServlet.PATH);
			LOGGER.error(e);
		}
	}
}
