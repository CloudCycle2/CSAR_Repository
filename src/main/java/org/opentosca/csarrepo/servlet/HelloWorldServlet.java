package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.service.ListCsarService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class HelloWorldServlet
 */
@WebServlet("/HelloWorldServlet")
public class HelloWorldServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;
	private static final String templateName = "helloworldservlet.ftl";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HelloWorldServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getTemplate(this.getServletContext(), templateName);
		try {
			ListCsarService csarService = new ListCsarService(0L);
			if (csarService.hasErrors()) {
				// FIXME, get all errors - not only first
				throw new ServletException("csarService has errors:" + csarService.getErrors().get(0));
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("csars", csarService.getResult());
			Template template = getTemplate(this.getServletContext(), templateName);
			template.process(root, response.getWriter());
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
