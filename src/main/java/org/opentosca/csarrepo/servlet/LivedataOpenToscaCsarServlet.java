package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.service.LivedataOpenToscaCsarService;
import org.opentosca.csarrepo.service.ShowOpenToscaServerService;
import org.opentosca.csarrepo.util.StringUtils;
import org.opentosca.csarrepo.util.jaxb.SimpleXLink;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author Dennis Przytarski
 */
@SuppressWarnings("serial")
@WebServlet(LivedataOpenToscaCsarServlet.PATH)
public class LivedataOpenToscaCsarServlet extends AbstractServlet {

	private static final String TEMPLATE_NAME = "livedataOpenToscaServerCsar.ftl";
	public static final String PATH = "/livedata/opentoscaserver/csar/*";

	public LivedataOpenToscaCsarServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = checkUserAuthentication(request, response);
			Map<String, Object> root = getRoot(request);
			Template template = getTemplate(this.getServletContext(), TEMPLATE_NAME);

			long openToscaServerId = StringUtils.getURLParameter(request.getPathInfo());
			ShowOpenToscaServerService showService = new ShowOpenToscaServerService(user.getId(), openToscaServerId);

			if (showService.hasErrors()) {
				AbstractServlet.addErrors(request, showService.getErrors());
				root.put("errorMessages", StringUtils.join(showService.getErrors()));
			} else {
				OpenToscaServer openToscaServer = showService.getResult();

				// get deployed csar data
				LivedataOpenToscaCsarService livedataOpenToscaCsarService = new LivedataOpenToscaCsarService(
						user.getId(), openToscaServer);
				root.put("openToscaServer", openToscaServer);

				List<SimpleXLink> deployedCsars = new ArrayList<SimpleXLink>();
				if (livedataOpenToscaCsarService.hasErrors()) {
					root.put("errorMessages", StringUtils.join(livedataOpenToscaCsarService.getErrors()));
				} else {
					deployedCsars = livedataOpenToscaCsarService.getResult();
				}
				root.put("deployedCsars", deployedCsars);
			}

			template.process(root, response.getWriter());
		} catch (AuthenticationException e) {
			return;
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
