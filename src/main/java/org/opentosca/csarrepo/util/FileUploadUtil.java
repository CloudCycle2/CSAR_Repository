package org.opentosca.csarrepo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

public class FileUploadUtil {
	/**
	 * Uploads a file to the targetPath
	 * 
	 * @param request - the upload request
	 * @param targetPath - the path to upload the file to
	 * @return a list of uploaded files, or an empty list, if no files were uploaded
	 * @throws FileUploadException - Exception when uploading failed
	 * @throws IOException - Exception, when some Input Outputstreams failed
	 */
	public static List<File> processUpload(final HttpServletRequest request, final String targetPath) throws FileUploadException, IOException {
		// Check if we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		List<File> filesUploaded = new ArrayList<File>();
		if (isMultipart) {
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload();
			
			// Parse the request
			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				InputStream stream = item.openStream();
				if (item.isFormField()) {
					String value = Streams.asString(stream);
					request.setAttribute(item.getFieldName(), value);
				} else {
					// Process the input stream
					String name = item.getName();
					File file = new File(targetPath + "/" + System.currentTimeMillis() + "_" + name.substring(name.lastIndexOf(File.separator) + 1));
					if (!file.exists() || file.canWrite()) {
						FileOutputStream fos = new FileOutputStream(file);
						Streams.copy(stream, fos, true);
						filesUploaded.add(file);
					}
				}
			}
		}
		return filesUploaded;
	}
}
