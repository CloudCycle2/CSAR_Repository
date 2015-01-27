package org.opentosca.csarrepo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opentosca.csarrepo.exception.PersistenceException;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.model.WineryServer;
import org.opentosca.csarrepo.model.repository.CsarFileRepository;
import org.opentosca.csarrepo.model.repository.WineryServerRepository;

@SuppressWarnings("serial")
@WebServlet(ExportToWineryServlet.PATH)
public class ExportToWineryServlet extends AbstractServlet {

	private static final String PARAM_CSARFILE_ID = "csarfileId";
	private static final String PARAM_WS_ID = "wineryId";
	public static final String PATH = "/exporttowinery";
	
	public ExportToWineryServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendError(405, "Method Not Allowed");

	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// load and validate file
		long fileId = Long.parseLong(request.getParameter(PARAM_CSARFILE_ID));
		CsarFileRepository csarFileRepo = new CsarFileRepository();
		try {
			CsarFile csarFile = csarFileRepo.getbyId(fileId);
		} catch (PersistenceException e) {
			System.out.println("loading file info failed");
			response.getWriter().write("loading csarfile failed");
			return;
		}
		
		// load and validate winery server
		long wineryId = Long.parseLong(request.getParameter(PARAM_WS_ID));
		WineryServerRepository wineryServerRepo = new WineryServerRepository();
		try {
			WineryServer wineryServer = wineryServerRepo.getbyId(wineryId);
		} catch(PersistenceException e) {
			System.out.println("loading winery failed");
			response.getWriter().write("loading winery failed");
			return;
		}
	}

}
