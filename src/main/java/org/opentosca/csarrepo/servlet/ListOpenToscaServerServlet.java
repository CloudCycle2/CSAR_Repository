package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.service.ListOpenToscaServerService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
@WebServlet(ListOpenToscaServerServlet.PATH)
public class ListOpenToscaServerServlet extends AbstractServlet {

	private static final String TEMPLATE_NAME = "listOpenToscaServerServlet.ftl";
	public static final String PATH = "/opentoscaserverlist";

	public ListOpenToscaServerServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = checkUserAuthentication(request, response);

			// setup output and template
			Map<String, Object> root = getRoot(request);
			Template template = getTemplate(this.getServletContext(), TEMPLATE_NAME);

			// init title
			root.put("title", "All OpenTOSCA-Servers");

			// invoke service
			// TODO: user handling
			ListOpenToscaServerService service = new ListOpenToscaServerService(user.getId());
			if (service.hasErrors()) {
				throw new ServletException("errors occured generating openTOSCA list" + service.getErrors().get(0));
			}

			// pass result to template
			root.put("opentoscaservers", service.getResult());

			// output
			template.process(root, response.getWriter());
		} catch (AuthenticationException e) {
			return;
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
