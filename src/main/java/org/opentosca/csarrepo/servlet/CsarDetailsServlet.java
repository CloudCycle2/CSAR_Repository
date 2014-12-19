package org.opentosca.csarrepo.servlet;

import java.io.IOException;
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
@SuppressWarnings("serial")
@WebServlet(CsarDetailsServlet.PATH)
public class CsarDetailsServlet extends AbstractServlet {

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
			Map<String, Object> root = getRoot();
			Template template = getTemplate(this.getServletContext(), TEMPLATE_NAME);

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

			Csar result = showService.getResult();
			// result.getCsarFiles().get(0).getha
			root.put("csar", result);
			root.put("csarFiles", result.getCsarFiles());
			root.put("title", String.format("%s: %s", result.getId(), result.getName()));
			template.process(root, response.getWriter());
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}
}
