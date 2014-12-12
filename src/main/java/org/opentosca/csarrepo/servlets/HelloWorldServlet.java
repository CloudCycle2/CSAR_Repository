package org.opentosca.csarrepo.servlets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.service.ListCsarService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * Servlet implementation class HelloWorldServlet
 */
@WebServlet("/HelloWorldServlet")
public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Configuration cfg = new Configuration(Configuration.VERSION_2_3_21);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorldServlet() {
        super();
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		cfg.setDirectoryForTemplateLoading(new File(this.getServletContext().getRealPath("/")));
		Template template = cfg.getTemplate("helloworldservlet.ftl");
		try {
			ListCsarService csarService = new ListCsarService(0L);
			if (csarService.hasErrors()) {
				// FIXME, get all errors - not only first
				throw new ServletException("csarService has errors:" + csarService.getErrors().get(0));
			}
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("csars", csarService.getResult());
			template.process(root, response.getWriter());
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
