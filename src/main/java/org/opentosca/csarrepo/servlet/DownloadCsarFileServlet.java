package org.opentosca.csarrepo.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.exception.AuthenticationException;
import org.opentosca.csarrepo.model.User;
import org.opentosca.csarrepo.service.DownloadCsarFileService;
import org.opentosca.csarrepo.util.DownloadCsarFileObject;

/**
 * Servlet implementation for downloading CSAR files
 *
 * @author Dennis Przytarski, Thomas Kosch (mail@thomaskosch.de)
 *
 */
@SuppressWarnings("serial")
@WebServlet(DownloadCsarFileServlet.PATH)
public class DownloadCsarFileServlet extends AbstractServlet {

	private static final Logger LOGGER = LogManager.getLogger(DownloadCsarFileServlet.class);

	private static final String PARAM_CSAR_FILE_ID = "csarfileid";
	private static final int BUFFER_SIZE = 4096;
	public static final String PATH = "/downloadcsarfile";

	public DownloadCsarFileServlet() {
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

			DownloadCsarFileService downloadService = new DownloadCsarFileService(user.getId(), Long.valueOf(request
					.getParameter(PARAM_CSAR_FILE_ID)));

			if (downloadService.hasErrors()) {
				AbstractServlet.addErrors(request, downloadService.getErrors());
				throw new ServletException("Could not get CsarFile from given CsarFileId");
			}

			DownloadCsarFileObject downloadCsarFileObject = downloadService.getResult();
			File file = downloadCsarFileObject.getFile();
			String filename = downloadCsarFileObject.getFilename();

			ServletOutputStream outputStream = response.getOutputStream();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			String contentDisposition = String.format("attachment; filename=%s", '"' + filename + '"');
			response.setHeader("Content-Disposition", contentDisposition);
			byte[] byteBuffer = new byte[BUFFER_SIZE];
			DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
			int length = 0;
			while ((null != dataInputStream) && ((length = dataInputStream.read(byteBuffer)) != -1)) {
				outputStream.write(byteBuffer, 0, length);
			}
			dataInputStream.close();
			outputStream.close();
		} catch (AuthenticationException e) {
			return;
		} catch (Exception e) {
			AbstractServlet.addError(request, e.getMessage());
			this.redirect(request, response, DashboardServlet.PATH);
			LOGGER.error(e);
		}
	}
}
