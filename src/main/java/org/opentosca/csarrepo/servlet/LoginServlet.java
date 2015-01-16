package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
@WebServlet(LoginServlet.PATH)
public class LoginServlet extends AbstractServlet {

	private static final String TEMPLATE_NAME = "login.ftl";
	public static final String PATH = "/login";

	public LoginServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// setup output and template
		Map<String, Object> root = getRoot();
		Template template = getTemplate(this.getServletContext(), TEMPLATE_NAME);

		// init title
		root.put("title", "Login");

		try {
			template.process(root, response.getWriter());
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// setup output and template
		Map<String, Object> root = getRoot();
		Template template = getTemplate(this.getServletContext(), TEMPLATE_NAME);

		// init title
		root.put("title", "Login");

		try {
			template.process(root, response.getWriter());
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
