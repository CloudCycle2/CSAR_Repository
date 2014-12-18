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
public abstract class AbstractServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
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
