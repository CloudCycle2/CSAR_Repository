package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.HashMap;
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
@WebServlet("")
public class DashboardServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;
	private static final String templateName = "dashboard.ftl";

	public DashboardServlet() {
		super(templateName);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getTemplate(this.getServletContext(), templateName);
		try {
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("basePath", this.getServletContext().getContextPath());
			Template template = getTemplate(this.getServletContext(), templateName);
			template.process(root, response.getWriter());
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
