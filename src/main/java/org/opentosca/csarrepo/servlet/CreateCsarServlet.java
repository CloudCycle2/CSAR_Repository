package org.opentosca.csarrepo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.service.CreateCsarService;

/**
 * Servlet implementation class UploadCSARServlet
 */
@SuppressWarnings("serial")
@WebServlet(CreateCsarServlet.PATH)
public class CreateCsarServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(CreateCsarServlet.class);
	private static final String PARAM_CSAR_NAME = "csarName";
	public static final String PATH = "/createcsar";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateCsarServlet() {
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
		checkUserAuthentication(request, response);

		// TODO: Check if Csar already exists and if it is empty
		String csarName = request.getParameter(PARAM_CSAR_NAME);
		if (csarName.isEmpty()) {
			this.addError(request, "Parameter csarName is empty.");
		} else {
			CreateCsarService createCsarService = new CreateCsarService(0L, csarName);

			LOGGER.debug("Got request to create CSAR " + csarName + " delegating ...");

			if (createCsarService.hasErrors()) {
				this.addErrors(request, createCsarService.getErrors());
			}
		}

		this.redirect(request, response, ListCsarServlet.PATH);
	}
}
