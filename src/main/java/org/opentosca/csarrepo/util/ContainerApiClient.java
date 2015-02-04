/**
 * 
 */
package org.opentosca.csarrepo.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
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

/**
 * This class establishes a connection to a given ContainerAPI URL
 * 
 * It enables a User of this class to upload a CSAR and trigger its deployment
 * 
 * @author Marcus Eisele (marcus.eisele@gmail.com)
 *
 */
public class ContainerApiClient {

	private WebTarget baseWebTarget;
	private Client client;

	public ContainerApiClient(URL address) {
		URI uri = null;
		try {
			// TODO: check if we should change the address to a URI or string
			uri = address.toURI();
			// TODO: set chunk size
			// http://stackoverflow.com/questions/11176824/preventing-the-jersey-client-from-causing-an-outofmemory-error-when-posting-larg
			client = ClientBuilder.newClient(new ClientConfig().register(MultiPartFeature.class));

			baseWebTarget = client.target(uri);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean uploadCSAR(CsarFile csarFile) {

		FileSystem fs = new FileSystem();
		File file = fs.getFile(csarFile.getHashedFile().getFilename());

		if (!file.exists()) {
			System.out.println("Error: file does not exist.");
			return false;
		}

		// build the message
		FormDataMultiPart multiPart = new FormDataMultiPart();
		FormDataContentDisposition.FormDataContentDispositionBuilder dispositionBuilder = FormDataContentDisposition
				.name("file");
		dispositionBuilder.fileName(csarFile.getName());
		dispositionBuilder.size(file.getTotalSpace());
		FormDataContentDisposition formDataContentDisposition = dispositionBuilder.build();

		multiPart.bodyPart(new FormDataBodyPart("file", file, MediaType.APPLICATION_OCTET_STREAM_TYPE)
				.contentDisposition(formDataContentDisposition));

		Entity<FormDataMultiPart> entity = Entity.entity(multiPart, MediaType.MULTIPART_FORM_DATA_TYPE);

		// submit the request
		WebTarget path = baseWebTarget.path("CSARs");
		Builder request = path.request();
		Response response = request.post(entity);

		// handle response
		if (Status.CREATED.getStatusCode() == response.getStatus()) {
			return true;
		} else {
			// TODO: logger
			// TODO: generate error, but i don't think we will get more than
			// that because OpenTosca just returns a 500 without additional
			// details
			return false;
		}
	}
}
