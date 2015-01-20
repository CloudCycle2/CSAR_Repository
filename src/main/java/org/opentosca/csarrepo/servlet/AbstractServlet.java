package org.opentosca.csarrepo.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.internal.util.Base64;
import org.opentosca.csarrepo.model.User;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * Abstraction for the servlet implementations
 * 
 * @author eiselems (marcus.eisele@gmail.com), Thomas Kosch
 *         (mail@thomaskosch.com), Dennis Przytarski
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractServlet extends HttpServlet {

	private static final Logger LOGGER = LogManager.getLogger(AbstractServlet.class);
	private Configuration cfg = new Configuration(Configuration.VERSION_2_3_21);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AbstractServlet() {
		super();
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
	}

	/**
	 * This method is overloaded to implement the get functionality
	 * 
	 * !Don't forget to invoke prepareServlet before doing anything with the
	 * template!
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected abstract void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException;

	public Map<String, Object> getRoot() {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("basePath", getBasePath());
		return root;
	}

	public String getBasePath() {
		return this.getServletContext().getContextPath();
	}

	public User checkAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);

		// Session management for user interface
		if (null == session && null != request.getAttribute("user") && !(request.getAttribute("user") instanceof User)) {
			LOGGER.info("Check of authentication failed: Session is null!");
			response.sendRedirect(LoginServlet.PATH);
		} else {
			return (User) request.getAttribute("user");
		}

		// Basic authentication for REST API
		String header = request.getHeader("Authorization");
		if (!header.substring(0, 6).equals("Basic ")) {
			LOGGER.info("Basic Authorization: Invalid credentials");
			response.sendError(401);
			return null;
		} else {
			String[] credentials = Base64.decodeAsString(header.substring(6).getBytes()).split(":");
			String username = credentials[0];
			String password = credentials[1];
			if (!(username.equals("admin") && password.equals("admin"))) {
				LOGGER.info("Basic Authorization: Invalid credentials ({}:{})", username, password);
				response.sendError(401);
			}
			// FIXME: Needs to be fixed, User has to be loaded from the database
			return new User();
		}
	}

	/**
	 * @param sc
	 * @param templateName
	 * @throws IOException
	 */
	public Template getTemplate(ServletContext sc, String templateName) throws IOException {
		cfg.setDirectoryForTemplateLoading(new File(this.getServletContext().getRealPath("/")));
		return cfg.getTemplate(templateName);
	}
}
