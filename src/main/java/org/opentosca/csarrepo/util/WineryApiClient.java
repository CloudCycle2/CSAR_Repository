package org.opentosca.csarrepo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.opentosca.csarrepo.filesystem.FileSystem;
import org.opentosca.csarrepo.model.CsarFile;

public class WineryApiClient {

	private Client client;
	private String url;

	public WineryApiClient(URL url) {
		this.url = url.toExternalForm();
		this.client = ClientBuilder.newClient(new ClientConfig()
				.register(MultiPartFeature.class));
	}

	public void uploadToWinery(CsarFile file) throws Exception {
		FileSystem fs = new FileSystem();
		File f = fs.getFile(file.getHashedFile().getFilename());

		if (f == null) {
			throw new FileNotFoundException(file.getName() + " not found");
		}

		// build form data
		FormDataMultiPart multiPart = new FormDataMultiPart();
		FormDataContentDisposition.FormDataContentDispositionBuilder dispositionBuilder = FormDataContentDisposition
				.name("file");

		dispositionBuilder.fileName(file.getName());
		dispositionBuilder.size(f.getTotalSpace());
		FormDataContentDisposition formDataContentDisposition = dispositionBuilder
				.build();

		multiPart.bodyPart(new FormDataBodyPart("file", f,
				MediaType.APPLICATION_OCTET_STREAM_TYPE)
				.contentDisposition(formDataContentDisposition));

		Entity<FormDataMultiPart> entity = Entity.entity(multiPart,
				MediaType.MULTIPART_FORM_DATA_TYPE);
		
		// send request
		WebTarget target = client.target(this.url + "/winery");
		Builder request = target.request();
		Response response = request.post(entity);
		
		
		
		// handle response
		if(Status.NO_CONTENT.getStatusCode() == response.getStatus()) {
			System.out.println("Pushed to winery");
			return;
		}
		
		throw new Exception("failed to push to winery");
	}

}
