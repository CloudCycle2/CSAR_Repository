package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.service.ListUserService;

import freemarker.template.Template;

@SuppressWarnings("serial")
@WebServlet(ListUserServlet.PATH)
public class ListUserServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(ListUserServlet.class);

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
			ListUserService listUserService = new ListUserService(user.getId());
			if (listUserService.hasErrors()) {
				AbstractServlet.addErrors(request, listUserService.getErrors());
				throw new ServletException("errors occured generating user list");
			}

			// pass result to template
			root.put("users", listUserService.getResult());

			// output
			template.process(root, response.getWriter());
		} catch (AuthenticationException e) {
			return;
		} catch (Exception e) {
			AbstractServlet.addError(request, e.getMessage());
			this.redirect(request, response, DashboardServlet.PATH);
			LOGGER.error(e);
		}
	}

}
