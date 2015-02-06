/**
 * 
 */
package org.opentosca.csarrepo.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.opentosca.csarrepo.exception.DeploymentException;
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
// TODO: refactor class to use the client generated in the constructor for all
// methods
public class ContainerApiClient {

	private WebTarget baseWebTarget;
	private Client client;

	private static final Logger LOGGER = LogManager.getLogger(ContainerApiClient.class);

	/**
	 * Creates a ContainerApiClient which connects to the given URI
	 * 
	 * @param address
	 * @throws URISyntaxException
	 */
	public ContainerApiClient(URI address) {
		// TODO: set chunk size
		// http://stackoverflow.com/questions/11176824/preventing-the-jersey-client-from-causing-an-outofmemory-error-when-posting-larg
		client = ClientBuilder.newClient(new ClientConfig().register(MultiPartFeature.class));
		baseWebTarget = client.target(address);
	}

	/**
	 * Uploads a CsarFile and triggers its processing
	 * 
	 * @param csarFile
	 * @return the location where the instance was created
	 */
	public String uploadCSAR(CsarFile csarFile) throws DeploymentException {

		FileSystem fs = new FileSystem();
		File file = fs.getFile(csarFile.getHashedFile().getFilename());

		if (!file.exists()) {
			throw new DeploymentException(
					String.format("File %s doesn't exist", csarFile.getHashedFile().getFilename()));
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
			return response.getHeaderString("location");
		} else {
			LOGGER.warn("Failed to deploy CSAR: " + csarFile.getName() + " to " + path);
			throw new DeploymentException("Deployment failed - OpenTOSCA Server returned " + response.getStatus());
		}
	}

	/**
	 * Submits a Delete at the given location
	 * 
	 * @param location
	 *            where the DELETE will be submitted
	 * @throws DeploymentException
	 *             when the deletion fails
	 */
	public void deleteCsarAtLocation(String location) throws DeploymentException {
		Client client = ClientBuilder.newClient(new ClientConfig().register(MultiPartFeature.class));
		WebTarget deleteTarget = client.target(location);
		Builder request = deleteTarget.request();
		Response response = request.delete();
		if (Status.OK.getStatusCode() == response.getStatus()) {
			return;
		} else {
			LOGGER.warn("Failed to delete CSAR at: " + location);
			throw new DeploymentException("Deletion failed - OpenTOSCA Server returned " + response.getStatus());
		}

	}
}
