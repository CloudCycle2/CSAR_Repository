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
@WebServlet("/createcsar")
public class CreateCsarServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LogManager.getLogger(CreateCsarServlet.class);
	private static final String PARAM_CSAR_NAME = "csarName";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateCsarServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @throws IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException {
		String csarName = request.getParameter(PARAM_CSAR_NAME);
		CreateCsarService createCsarService = new CreateCsarService(0L, csarName);

		LOGGER.debug("Got request to create CSAR " + csarName + " delegating ...");

		if (createCsarService.hasErrors()) {
			throw new ServletException("CreateCsarService has Errors: " + createCsarService.getErrors().get(0));
		} else {
			// TODO write proper response
			response.getWriter().print("<html><body>Success</body></html>");
		}
	}
}
