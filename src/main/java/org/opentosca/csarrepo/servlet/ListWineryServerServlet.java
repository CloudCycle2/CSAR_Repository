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
import org.opentosca.csarrepo.service.ListWineryServerService;

import freemarker.template.Template;

@SuppressWarnings("serial")
@WebServlet(ListWineryServerServlet.PATH)
public class ListWineryServerServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(ListWineryServerServlet.class);

	private static final String TEMPLATE_NAME = "listWineryServerServlet.ftl";
	public static final String PATH = "/winerylist";

	public ListWineryServerServlet() {
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
			root.put("title", "Winery Servers");

			// invoke service
			ListWineryServerService listWineryServerService = new ListWineryServerService(user.getId());
			if (listWineryServerService.hasErrors()) {
				AbstractServlet.addErrors(request, listWineryServerService.getErrors());
				throw new ServletException("errors occured generating winery list");
			}

			// pass result to template
			root.put("wineryServers", listWineryServerService.getResult());

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
