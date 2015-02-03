package org.opentosca.csarrepo.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.service.UploadCsarFileService;

/**
 * Servlet implementation class UploadCSARServlet
 */
@SuppressWarnings("serial")
@WebServlet(UploadCsarFileServlet.PATH)
public class UploadCsarFileServlet extends AbstractServlet {

	private static final String PARAM_CSAR_ID = "csarId";
	public static final String PATH = "/uploadcsarfile";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadCsarFileServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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

			// Check if we have a file upload request
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			// check if it is actually a file
			if (isMultipart) {
				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload();

				// Parse the request
				FileItemIterator iter = upload.getItemIterator(request);
				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					InputStream stream = item.openStream();
					// Process the input stream
					String pathName = item.getName();
					// Path from local Upload of a user is the full path
					// get the actual last part of the path
					// (/foo/bar/uploadcsar.csar => uploadcsar.csar)
					String csarName = pathName.substring(pathName.lastIndexOf(File.separator) + 1);
					Long csarId = Long.parseLong(request.getParameter(PARAM_CSAR_ID));

					UploadCsarFileService upService = new UploadCsarFileService(user.getId(), csarId, stream, csarName);
					// TODO, think about better Exceptionhandling (currently we
					// just take first Exception)
					if (upService.hasErrors()) {
						throw new ServletException("UploadCsarService has Errors: " + upService.getErrors().get(0));
					}

					this.redirect(request, response, CsarDetailsServlet.PATH.replace("*", csarId.toString()));
				}
			}
		} catch (AuthenticationException e) {
			return;
		} catch (FileUploadException | NumberFormatException | ServletException e) {
			response.getWriter().print(e.getMessage());
		}
	}
}
