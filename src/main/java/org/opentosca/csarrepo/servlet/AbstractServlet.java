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
import org.opentosca.csarrepo.service.LoadCheckedUserService;
import org.opentosca.csarrepo.util.Hash;

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
		User user;
		if (null != request.getHeader("Authorization")) {
			user = this.checkBasicAuthentication(request, response);
			if (null == user) {
				LOGGER.info("User object for basic authentication is null");
				response.sendError(401);
			}
		} else {
			user = this.checkUserAuthentication(request);
			if (null == user) {
				LOGGER.info("User object for user authentication is null");
				response.sendRedirect(getBasePath() + LoginServlet.PATH);
			}
		}
		return user;
	}

	public User checkBasicAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Basic authentication for REST API
		String header = request.getHeader("Authorization");
		if (header.substring(0, 6).equals("Basic ")) {
			String[] credentials = Base64.decodeAsString(header.substring(6).getBytes()).split(":");
			if (credentials.length > 1) {
				String username = credentials[0];
				String password = credentials[1];
				String hashedPassword = Hash.sha256(password);
				LoadCheckedUserService loadCheckedUserService = new LoadCheckedUserService(username, hashedPassword);
				if (!loadCheckedUserService.hasErrors()) {
					return loadCheckedUserService.getResult();
				} else {
					// TODO: Make this crap clean: Don't return a string list
					LOGGER.info("Failed to get user: " + loadCheckedUserService.getErrors().get(0));
				}
			}
		} else {
			LOGGER.info("Basic Authorization: Invalid credentials");
		}
		return null;
	}

	public User checkUserAuthentication(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (null != session && null != session.getAttribute("user") && session.getAttribute("user") instanceof User) {
			return (User) session.getAttribute("user");
		} else {
			LOGGER.info("User object does not exist!");
			return null;
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
