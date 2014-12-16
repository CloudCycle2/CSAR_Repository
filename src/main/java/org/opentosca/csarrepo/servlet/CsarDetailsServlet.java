package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.service.ShowCsarService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class HelloWorldServlet
 */
@WebServlet("/Csar/*")
public class CsarDetailsServlet extends AbstractServlet {

	private static final long serialVersionUID = -1353913818073048397L;
	private static final String templateName = "csardetailsservlet.ftl";

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
			// TODO: length-check
			String[] pathInfo = request.getPathInfo().split("/");
			// TODO: handle exception
			long csarId = Long.parseLong(pathInfo[1]); // {id}

			// TODO: add real UserID
			ShowCsarService showService = new ShowCsarService(0L, csarId);
			if (showService.hasErrors()) {
				// FIXME, get all errors - not only first
				throw new ServletException("csarService has errors:" + showService.getErrors().get(0));
			}

			Map<String, Object> root = new HashMap<String, Object>();
			Csar result = showService.getResult();
			root.put("csar", result);
			root.put("csarFiles", result.getCsarFiles());

			Template template = getTemplate(this.getServletContext(), templateName);
			template.process(root, response.getWriter());
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
