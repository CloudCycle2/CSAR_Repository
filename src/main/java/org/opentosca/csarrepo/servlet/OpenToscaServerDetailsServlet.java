package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.service.ShowOpenToscaServerService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class HelloWorldServlet
 */
@SuppressWarnings("serial")
@WebServlet(OpenToscaServerDetailsServlet.PATH)
public class OpenToscaServerDetailsServlet extends AbstractServlet {

	private static final String TEMPLATE_NAME = "OpenToscaServerDetailsServlet.ftl";
	public static final String PATH = "/opentoscaserver/*";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OpenToscaServerDetailsServlet() {
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
			long openToscaServerId = Long.parseLong(pathInfo[1]); // {id}

			ShowOpenToscaServerService showService = new ShowOpenToscaServerService(user.getId(), openToscaServerId);
			if (showService.hasErrors()) {
				this.addErrors(request, showService.getErrors());
				this.redirect(request, response,
						OpenToscaServerDetailsServlet.PATH.replace("*", String.valueOf(openToscaServerId)));
				return;
			}

			OpenToscaServer result = showService.getResult();
			root.put("openToscaServer", result);
			root.put("cloudInstances", result.getCloudInstances());
			root.put("title", String.format("%s: %s", result.getId(), result.getName()));
			template.process(root, response.getWriter());
		} catch (AuthenticationException e) {
			return;
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
