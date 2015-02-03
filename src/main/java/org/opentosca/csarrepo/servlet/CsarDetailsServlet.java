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
import org.opentosca.csarrepo.service.ShowCsarService;

import freemarker.template.Template;

/**
 * Servlet implementation class HelloWorldServlet
 */
@SuppressWarnings("serial")
@WebServlet(CsarDetailsServlet.PATH)
public class CsarDetailsServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(CsarDetailsServlet.class);

	private static final String TEMPLATE_NAME = "csardetailsservlet.ftl";
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
			// TODO: length-check
			String[] pathInfo = request.getPathInfo().split("/");
			// TODO: handle exception
			long csarId = Long.parseLong(pathInfo[1]); // {id}
			// TODO: add real UserID
			ShowCsarService showService = new ShowCsarService(user.getId(), csarId);
			if (showService.hasErrors()) {
				this.addErrors(request, showService.getErrors());
				return;
			}
			Csar result = showService.getResult();
			// result.getCsarFiles().get(0).getha
			root.put("csar", result);
			root.put("csarFiles", result.getCsarFiles());
			root.put("title", String.format("%s: %s", result.getId(), result.getName()));
			template.process(root, response.getWriter());
		} catch (AuthenticationException e) {
			return;
		} catch (Exception e) {
			this.addError(request, e.getMessage());
			this.redirect(request, response, DashboardServlet.PATH);
			LOGGER.error(e);
		}
	}

}
