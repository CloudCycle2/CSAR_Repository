package org.opentosca.csarrepo.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.service.ImportCsarFromWineryService;
import org.opentosca.csarrepo.service.ShowCsarService;
import org.opentosca.csarrepo.service.ShowWineryServerService;
import org.opentosca.csarrepo.service.WineryServicetemplateListService;

import freemarker.template.Template;

/**
 * Servlet for creation of winery server
 */
@SuppressWarnings("serial")
@WebServlet(NewVersionFromWineryServlet.PATH)
public class NewVersionFromWineryServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(NewVersionFromWineryServlet.class);

	private static final String PARAM_WINERY_SERVER_ID = "wineryId";
	private static final String PARAM_SERVICETEMPLATE = "servicetemplate";
	private static final String PARAM_CSAR_ID = "csarId";
	public static final String PATH = "/newversionfromwinery";
	public static final String TEMPLATE_NAME = "newVersionFromWinery.ftl";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewVersionFromWineryServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(405, "Method Not Allowed");
	}

	/**
	 * @throws IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			User user = checkUserAuthentication(request, response);

			long wineryId = 0;
			long csarId = 0;
			try {
				wineryId = Long.parseLong(request.getParameter(PARAM_WINERY_SERVER_ID));
				csarId = Long.parseLong(request.getParameter(PARAM_CSAR_ID));
			} catch(NumberFormatException e) {
				// TODO handle invalid wineryid / csarid
			}
			
			// load the csar to check if a service template is set
			ShowCsarService showCsarService = new ShowCsarService(user.getId(), csarId);
			String servicetemplate = request.getParameter(PARAM_SERVICETEMPLATE);
			
			if(showCsarService.hasErrors()) {
				addErrors(request, showCsarService.getErrors());
				
				return;
			}
			
			ImportCsarFromWineryService importService;
			
			if(showCsarService.getResult().getServiceTemplateId() == null) {
				// no service template set in csar --> new service template
				if(!servicetemplate.equals("")) {
					// service template set in form data --> use
					importService = new ImportCsarFromWineryService(user.getId(), wineryId, csarId, request.getParameter(PARAM_SERVICETEMPLATE));
				} else {
					// service template not set in form data --> list available service templates
					ShowWineryServerService winery = new ShowWineryServerService(user.getId(), wineryId);
					if(winery.hasErrors()) {
						AbstractServlet.addErrors(request, winery.getErrors());
						redirect(request, response, CsarDetailsServlet.PATH.replace("*", ""+showCsarService.getResult().getId()));
						return;
					}
					WineryServicetemplateListService stList = new WineryServicetemplateListService(user.getId(), winery.getResult().getAddress());
					if(stList.hasErrors()) {
						AbstractServlet.addErrors(request, stList.getErrors());
						redirect(request, response, CsarDetailsServlet.PATH.replace("*", ""+showCsarService.getResult().getId()));
						return;
					}
					Map<String, Object> root = getRoot(request);
					Template template = getTemplate(this.getServletContext(), TEMPLATE_NAME);
					
					root.put("csar", showCsarService.getResult());
					root.put("winery", winery.getResult());
					root.put("servicetemplates", stList.getResult());
					
					template.process(root, response.getWriter());
					return;	
				}
			} else {
				String tmpNamespace = URLEncoder.encode(showCsarService.getResult().getNamespace(), "utf-8");
				String tmpServicetemplate = URLEncoder.encode(showCsarService.getResult().getServiceTemplateId(), "utf-8");
				tmpNamespace = URLEncoder.encode(tmpNamespace, "utf-8");
				tmpServicetemplate = URLEncoder.encode(tmpServicetemplate, "utf-8");
				servicetemplate = tmpNamespace + "/" + tmpServicetemplate + "/";
				importService = new ImportCsarFromWineryService(user.getId(), wineryId, csarId, servicetemplate);
			}
			
			if(importService.hasErrors()) {
				addErrors(request, importService.getErrors());
			} else {
				AbstractServlet.addSuccess(request, "Imported new version");
			}
			
			redirect(request, response, CsarDetailsServlet.PATH.replace("*", ""+showCsarService.getResult().getId()));
		} catch (AuthenticationException e) {
			return;
		} catch (Exception e) {
			AbstractServlet.addError(request, e.getMessage());
			this.redirect(request, response, DashboardServlet.PATH);
			LOGGER.error(e);
		}
	}
}
