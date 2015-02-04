package org.opentosca.csarrepo.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.opentosca.csarrepo.exception.AuthenticationException;
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

	private static final String ERRORS = "errors";
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

	public Map<String, Object> getRoot(HttpServletRequest request) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("basePath", this.getBasePath());
		root.put(ERRORS, request.getSession().getAttribute(ERRORS));
		request.getSession().setAttribute(ERRORS, new ArrayList<String>());
		return root;
	}

	/**
	 * @return the base path of the repository <code>/csarrepo</code>
	 */
	public String getBasePath() {
		return this.getServletContext().getContextPath();
	}

	/**
	 * Triggers authentication over HTTP
	 * 
	 * @param request
	 *            The incoming request for the servlet
	 * @param response
	 *            The outgoing response of the servlet
	 * @return The user
	 * @throws IOException
	 */
	public User checkBasicAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Basic authentication for REST API
		if (null != request.getHeader("Authorization")) {
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
						// TODO: log more than one error
						LOGGER.error(loadCheckedUserService.getErrors().get(0));
						response.sendError(401);
					}
				}
			}
		}
		LOGGER.info("Basic Authorization: Invalid credentials");
		response.sendError(401);
		return null;
	}

	/**
	 * This method uses the <code>request</code> session attribute <i>user</i>
	 * to find the matching user.
	 * 
	 * If no user is found it alters the given <code>request</code> to redirect
	 * to the login page.
	 * 
	 * @param request
	 *            The incoming request for the servlet
	 * @param response
	 *            The outgoing response
	 * @return the user
	 * 
	 * @throws AuthenticationException
	 *             if the user doesn't exist
	 * @throws IOException
	 */
	public User checkUserAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException,
			AuthenticationException {
		HttpSession session = request.getSession(false);
		if (null != session && null != session.getAttribute("user") && session.getAttribute("user") instanceof User) {
			return (User) session.getAttribute("user");
		} else {
			LOGGER.info("User object does not exist!");
			response.sendRedirect(getBasePath() + LoginServlet.PATH);
			throw new AuthenticationException("User object does not exist!");
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

	/**
	 * Wrapper method for redirecting responses with concatenation of the
	 * session id.
	 * 
	 * @param request
	 *            The servlet request
	 * @param response
	 *            The servlet response
	 * @param redirectPath
	 *            The redirect path
	 * @throws IOException
	 */
	protected void redirect(HttpServletRequest request, HttpServletResponse response, String redirectPath)
			throws IOException {
		response.sendRedirect(String.format("%s%s;jsessionid=%s", getBasePath(), redirectPath, request.getSession()
				.getId()));
	}

	protected static boolean hasErrors(HttpServletRequest request) {
		List<String> errors = (List<String>) request.getSession().getAttribute(ERRORS);
		return errors.size() > 0;
	}

	/**
	 * Get notification errors from the request.
	 * 
	 * @param request
	 * @return List of errors
	 */
	protected static List<String> getErrors(HttpServletRequest request) {
		return (List<String>) request.getSession().getAttribute(ERRORS);
	}

	/**
	 * Add an error to the error notification list.
	 * 
	 * @param request
	 *            where the error should be added
	 * @param error
	 */
	protected static void addError(HttpServletRequest request, String error) {
		((List<String>) request.getSession().getAttribute(ERRORS)).add(error);
	}

	/**
	 * Add an existing error list to the error notification list.
	 * 
	 * @param request
	 *            where the errors should be added
	 * @param errors
	 */
	protected static void addErrors(HttpServletRequest request, List<String> errors) {
		((List<String>) request.getSession().getAttribute(ERRORS)).addAll(errors);
	}

}
