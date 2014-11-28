package org.opentosca.csarrepo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/myresource")
public class MyResource {

	// FIXME: replace with correct tmp path
	private static final String SERVER_UPLOAD_LOCATION_FOLDER = "/Users/marcuseisele/tmp/";

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "It";
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail
	// ,@HeaderParam("Content-Length") int length
	) {

		// System.out.println(length);

		if (null == uploadedInputStream) {
			// TODO: logger
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("The stream is null.").build();
		}
		if (null == fileDetail) {
			return Response.serverError().entity("The file details are null.")
					.build();
		}

		String filePath = SERVER_UPLOAD_LOCATION_FOLDER
				+ fileDetail.getFileName();
		saveFile(uploadedInputStream, filePath);

		System.out.println("Post for uploading a new CSAR as file with name \""
				+ fileDetail.getFileName() + "\" with size "
		// always returns -1
		// + fileDetail.getSize() + "."
				);

		return Response.ok().entity("Stored file").build();

	}

	/**
	 * Writes uploadedInputStream into tempFile. Which is moved to fileLocation
	 * after completion.
	 * 
	 * @param uploadedInputStream
	 * @param fileLocation
	 */
	private void saveFile(InputStream uploadedInputStream, String fileLocation) {

		try {
			File tmpFile = File.createTempFile("tmpCSAR", ".tmp");
			tmpFile.deleteOnExit();

			OutputStream outputStream = new FileOutputStream(tmpFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			outputStream.close();

			// TODO: logger
			System.out
					.println("tmp file created: " + tmpFile.getAbsolutePath());

			// FIXME: move file to correct path on server
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
