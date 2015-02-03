package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.service.ListUserService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
@WebServlet(ListUserServlet.PATH)
public class ListUserServlet extends AbstractServlet {

	private static final String TEMPLATE_NAME = "listUserServlet.ftl";
	public static final String PATH = "/userlist";

	public ListUserServlet() {
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
			root.put("title", "Users");

			// invoke service
			ListUserService service = new ListUserService(user.getId());
			if (service.hasErrors()) {
				// TODO error handling...
				throw new ServletException("errors occured generating user list");
			}

			// pass result to template
			root.put("users", service.getResult());

			// output
			template.process(root, response.getWriter());
		} catch (AuthenticationException e) {
			return;
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
