package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.opentosca.csarrepo.service.LoadCheckedUserService;
import org.opentosca.csarrepo.util.Hash;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Login servlet for the Csar repository
 * 
 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.com)
 *
 */
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
		Map<String, Object> root = getRoot(request);
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
		if (null != request.getSession(false)) {
			request.getSession(false).invalidate();
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("errors", new ArrayList<String>());

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String hashedPassword = Hash.sha256(password);

		LoadCheckedUserService loadCheckedUserService = new LoadCheckedUserService(username, hashedPassword);

		if (loadCheckedUserService.hasErrors()) {
			AbstractServlet.addErrors(request, loadCheckedUserService.getErrors());
			this.redirect(request, response, LoginServlet.PATH);
			return;
		} else {
			session.setAttribute("user", loadCheckedUserService.getResult());
			this.redirect(request, response, DashboardServlet.PATH);
			return;
		}
	}
}
