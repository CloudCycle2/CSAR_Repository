package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.service.ListWineryServerService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
@WebServlet(ListWineryServerServlet.PATH)
public class ListWineryServerServlet extends AbstractServlet {

	private static final String TEMPLATE_NAME = "listWineryServerServlet.ftl";
	public static final String PATH = "/winerylist";

	public ListWineryServerServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			checkUserAuthentication(request, response);

			// setup output and template
			Map<String, Object> root = getRoot(request);
			Template template = getTemplate(this.getServletContext(), TEMPLATE_NAME);

			// init title
			root.put("title", "Winery servers");

			// invoke service
			ListWineryServerService service = new ListWineryServerService(0);
			if (service.hasErrors()) {
				// TODO error handling...
				throw new ServletException("errors occured generating winery list");
			}

			// pass result to template
			root.put("wineryServers", service.getResult());

			template.process(root, response.getWriter());
		} catch (AuthenticationException e) {
			return;
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
