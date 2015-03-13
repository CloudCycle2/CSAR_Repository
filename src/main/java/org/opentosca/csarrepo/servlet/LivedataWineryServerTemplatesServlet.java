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
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.model.WineryServer;
import org.opentosca.csarrepo.service.ShowWineryServerService;
import org.opentosca.csarrepo.service.WineryServicetemplateListService;
import org.opentosca.csarrepo.util.Servicetemplate;
import org.opentosca.csarrepo.util.StringUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author Dennis Przytarski
 */
@SuppressWarnings("serial")
@WebServlet(LivedataWineryServerTemplatesServlet.PATH)
public class LivedataWineryServerTemplatesServlet extends AbstractServlet {

	private static final String TEMPLATE_NAME = "livedataWineryServerTemplates.ftl";
	public static final String PATH = "/livedata/wineryserver/templates/*";

	public LivedataWineryServerTemplatesServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = checkUserAuthentication(request, response);
			Map<String, Object> root = getRoot(request);
			Template template = getTemplate(this.getServletContext(), TEMPLATE_NAME);

			long wineryServerId = StringUtils.getURLParameter(request.getPathInfo());
			ShowWineryServerService showWineryService = new ShowWineryServerService(user.getId(), wineryServerId);

			if (showWineryService.hasErrors()) {
				AbstractServlet.addErrors(request, showWineryService.getErrors());
				root.put("errorMessages", StringUtils.join(showWineryService.getErrors()));
			} else {
				WineryServer wineryServer = showWineryService.getResult();

				WineryServicetemplateListService stListService = new WineryServicetemplateListService(user.getId(),
						wineryServer.getAddress());
				root.put("wineryServer", wineryServer);

				List<Servicetemplate> serviceTemplates = new ArrayList<Servicetemplate>();
				if (stListService.hasErrors()) {
					root.put("errorMessages", StringUtils.join(stListService.getErrors()));
				} else {
					serviceTemplates = stListService.getResult();
				}
				root.put("servicetemplates", serviceTemplates);
			}

			template.process(root, response.getWriter());
		} catch (AuthenticationException e) {
			return;
		} catch (TemplateException e) {
			response.getWriter().print(e.getMessage());
		}
	}

}
