package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.service.ListCsarService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
@WebServlet("/csarlist")
public class ListCsarServlet extends AbstractServlet {

	private static final String TEMPLATE_NAME = "listCsarServlet.ftl";

	public ListCsarServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// setup output and template
		Map<String, Object> root = getRoot();
		Template template = getTemplate(this.getServletContext(), TEMPLATE_NAME);

		// init title
		root.put("title", "All CSARs");

		// invoke service
		ListCsarService service = new ListCsarService(0);
		if (service.hasErrors()) {
			// TODO error handling...
			throw new ServletException("errors occured generating csar list");
		}

		// pass result to template
		root.put("csars", service.getResult());

		// output
		try {
			template.process(root, response.getWriter());
		} catch (TemplateException e) {
			// TODO how to handle exceptions here...
			e.printStackTrace();
		}
	}

}
