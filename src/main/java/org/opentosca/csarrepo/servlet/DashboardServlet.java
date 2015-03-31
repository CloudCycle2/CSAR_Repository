package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.service.CountCsarFileService;
import org.opentosca.csarrepo.service.CountCsarPlanService;
import org.opentosca.csarrepo.service.CountCsarService;
import org.opentosca.csarrepo.service.CountDeployedCsarService;
import org.opentosca.csarrepo.service.CountHashedFileService;
import org.opentosca.csarrepo.service.CountOpenToscaServerService;
import org.opentosca.csarrepo.service.CountUserService;
import org.opentosca.csarrepo.service.CountWineryServerService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author Dennis Przytarski
 */
@SuppressWarnings("serial")
@WebServlet(DashboardServlet.PATH)
public class DashboardServlet extends AbstractServlet {

	private static final String TEMPLATE_NAME = "dashboard.ftl";
	public static final String PATH = "/dashboard";

	public DashboardServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = checkUserAuthentication(request, response);
			Map<String, Object> root = getRoot(request);
			Template template = getTemplate(this.getServletContext(), TEMPLATE_NAME);
			
			// invoke services
			CountCsarFileService csarFiles = new CountCsarFileService(user.getId());
			CountCsarPlanService csarPlans = new CountCsarPlanService(user.getId());
			CountCsarService csars = new CountCsarService(user.getId());
			CountDeployedCsarService deployedCsars = new CountDeployedCsarService(user.getId());
			CountHashedFileService hashedFiles = new CountHashedFileService(user.getId());
			CountOpenToscaServerService otServers = new CountOpenToscaServerService(user.getId());
			CountUserService users = new CountUserService(user.getId());
			CountWineryServerService wServers = new CountWineryServerService(user.getId());
			
			// check for errors
			if(csarFiles.hasErrors()) {
				AbstractServlet.addErrors(request, csarFiles.getErrors());
				root.put("csarFiles", 0);
			} else {
				root.put("csarFiles", csarFiles.getResult());
			}
			if(csarPlans.hasErrors()) {
				AbstractServlet.addErrors(request, csarPlans.getErrors());
				root.put("csarPlans", 0);
			} else {
				root.put("csarPlans", csarPlans.getResult());
			}
			if(csars.hasErrors()) {
				AbstractServlet.addErrors(request, csars.getErrors());
				root.put("csars", 0);
			} else {
				root.put("csars", csars.getResult());
			}
			if(deployedCsars.hasErrors()) {
				AbstractServlet.addErrors(request, deployedCsars.getErrors());
				root.put("deployedCsars", 0);
			} else {
				root.put("deployedCsars", deployedCsars.getResult());
			}
			if(hashedFiles.hasErrors()) {
				AbstractServlet.addErrors(request, hashedFiles.getErrors());
				root.put("hashedFiles", 0);
			} else {
				root.put("hashedFiles", hashedFiles.getResult());
			}
			if(otServers.hasErrors()) {
				AbstractServlet.addErrors(request, otServers.getErrors());
				root.put("otServers", 0);
			} else {
				root.put("otServers", otServers.getResult());
			}
			if(users.hasErrors()) {
				AbstractServlet.addErrors(request, users.getErrors());
				root.put("users", 0);
			} else {
				root.put("users", users.getResult());
			}
			if(wServers.hasErrors()) {
				AbstractServlet.addErrors(request, wServers.getErrors());
				root.put("wServers", 0);
			} else {
				root.put("wServers", wServers.getResult());
			}
			
			// render template
			root.put("title", "Dashboard");
			template.process(root, response.getWriter());
		} catch (AuthenticationException e) {
			return;
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}

	}

}
