package org.opentosca.csarrepo.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.opentosca.csarrepo.service.UploadCsarService;

/**
 * Servlet implementation class UploadCSARServlet
 */
@WebServlet("/UploadCSARServlet")
public class UploadCSARServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadCSARServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		try {
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
					UploadCsarService upService = new UploadCsarService(0, stream, csarName);
					// TODO, think about better Exceptionhandling (currently we
					// just take first Exception)
					if (upService.hasErrors()) {
						throw new ServletException("UploadCsarService has Errors: " + upService.getErrors().get(0));
					} else{
						// TODO write proper response
						response.getWriter().print("<html><body>Success</body></html>");
					}
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}