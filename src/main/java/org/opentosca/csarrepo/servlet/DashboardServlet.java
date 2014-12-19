package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author Dennis Przytarski
 */
@SuppressWarnings("serial")
@WebServlet(DashboardServlet.PATH)
public class DashboardServlet extends AbstractServlet {

	private static final String TEMPLATE_NAME = "dashboard.ftl";
	public static final String PATH = "";

	public DashboardServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Map<String, Object> root = getRoot();
			Template template = getTemplate(this.getServletContext(), TEMPLATE_NAME);
			template.process(root, response.getWriter());
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
