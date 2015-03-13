package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.service.ListLivedataOpenToscaCsarService;
import org.opentosca.csarrepo.service.ListLivedataOpenToscaInstancesService;
import org.opentosca.csarrepo.service.ShowOpenToscaServerService;
import org.opentosca.csarrepo.util.StringUtils;
import org.opentosca.csarrepo.util.jaxb.ServiceInstanceEntry;
import org.opentosca.csarrepo.util.jaxb.SimpleXLink;

import freemarker.template.Template;

/**
 * Implementation of the OpenTosca Servlet page
 */
@SuppressWarnings("serial")
@WebServlet(OpenToscaServerDetailsServlet.PATH)
public class OpenToscaServerDetailsServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(OpenToscaServerDetailsServlet.class);

	private static final String TEMPLATE_NAME = "openToscaServerDetailsServlet.ftl";
	public static final String PATH = "/opentoscaserver/*";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OpenToscaServerDetailsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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
				this.redirect(request, response, ListOpenToscaServerServlet.PATH);
				return;
			}

			OpenToscaServer openToscaServer = showService.getResult();
			// get live data
			ListLivedataOpenToscaInstancesService listLivedataOpenToscaInstancesService = new ListLivedataOpenToscaInstancesService(
					user.getId(), openToscaServer);
			// get deployed csar data
			ListLivedataOpenToscaCsarService listLivedataOpenToscaCsarService = new ListLivedataOpenToscaCsarService(
					user.getId(), openToscaServer);

			String concatedErrorList = "";
			
			List<ServiceInstanceEntry> instanceLiveDataList;
			List<SimpleXLink> csarLiveDataList;
			if (listLivedataOpenToscaInstancesService.hasErrors()) {
				concatedErrorList += StringUtils.join(listLivedataOpenToscaInstancesService.getErrors());
				instanceLiveDataList = new ArrayList<ServiceInstanceEntry>();
			} else {
				instanceLiveDataList = listLivedataOpenToscaInstancesService.getResult();
			}
			
			if (listLivedataOpenToscaCsarService.hasErrors()) {
				concatedErrorList += StringUtils.join(listLivedataOpenToscaCsarService.getErrors());
				csarLiveDataList = new ArrayList<SimpleXLink>();
			} else {
				csarLiveDataList = listLivedataOpenToscaCsarService.getResult();
			}
			
			root.put("openToscaMessage", concatedErrorList);

			root.put("openToscaServer", openToscaServer);
			root.put("title", String.format("%s: %s", openToscaServer.getId(), openToscaServer.getName()));

			// add live data
			root.put("liveEntries", instanceLiveDataList);
			root.put("deployedCsars", csarLiveDataList);

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
