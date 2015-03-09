/**
 * 
 */
package org.opentosca.csarrepo.util;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.ws.rs.ProcessingException;
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
import org.opentosca.csarrepo.model.OpenToscaServer;
import org.opentosca.csarrepo.rest.model.SimpleXLink;
import org.opentosca.csarrepo.util.jaxb.ServiceInstanceEntry;
import org.opentosca.csarrepo.util.jaxb.ServiceInstanceList;
import org.opentosca.csarrepo.util.jaxb2.References;

/**
 * This class establishes a connection to a given ContainerAPI URL
 * 
 * It enables a User of this class to upload a CSAR and trigger its deployment
 * 
 * @author Marcus Eisele (marcus.eisele@gmail.com), Dennis Przytarski, Thomas
 *         Kosch
 *
 */
public class ContainerApiClient {

	private WebTarget baseWebTarget;
	private Client client;
	private OpenToscaServer openToscaServer;

	private static final Logger LOGGER = LogManager.getLogger(ContainerApiClient.class);

	/**
	 * Creates a ContainerApiClient which connects to the given URI
	 * 
	 * @param address
	 * @throws URISyntaxException
	 */
	public ContainerApiClient(OpenToscaServer openToscaServer) throws URISyntaxException {
		// TODO: set chunk size
		// http://stackoverflow.com/questions/11176824/preventing-the-jersey-client-from-causing-an-outofmemory-error-when-posting-larg
		this.client = ClientBuilder.newClient(new ClientConfig().register(MultiPartFeature.class));
		this.openToscaServer = openToscaServer;
		// TODO: check if it possible to store address as URI instead of URL
		baseWebTarget = client.target(openToscaServer.getAddress().toURI());
	}

	/**
	 * Uploads a CsarFile and triggers its processing
	 * 
	 * @param csarFile
	 * @return the location where the instance was created
	 */
	public String uploadCSAR(CsarFile csarFile) throws DeploymentException {

		try {
			FileSystem fs = new FileSystem();
			File file = fs.getFile(csarFile.getHashedFile().getFilename());

			if (!file.exists()) {
				throw new DeploymentException(String.format("File %s doesn't exist", csarFile.getHashedFile()
						.getFilename()));
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
		} catch (ProcessingException e) {
			LOGGER.warn("Failed to upload CSAR: Server - server was not reachable", e);
			throw new DeploymentException("Deletion failed - OpenTOSCA Server was not reachable");
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
		try {
			WebTarget deleteTarget = client.target(location);
			Builder request = deleteTarget.request();
			Response response = request.delete();
			if (Status.OK.getStatusCode() == response.getStatus()) {
				return;
			} else {
				LOGGER.warn("Failed to delete CSAR at: " + location);
				throw new DeploymentException("Deletion failed - OpenTOSCA Server returned " + response.getStatus());
			}
		} catch (ProcessingException e) {
			LOGGER.warn("Failed to delete CSAR at: " + location + " Server was not reachable.", e);
			throw new DeploymentException("Deletion failed - OpenTOSCA Server was not reachable");
		}

	}

	/**
	 * Submits a GET on the instancedata/serviceInstances Path of the given
	 * openToscaServer
	 * 
	 * @param openToscaServer
	 * @return
	 * @throws DeploymentException
	 * 
	 */

	// TODO: maybe we can extend the containerAPI to supply all needed
	// attributes inside the serviceInstances resource directly
	public ArrayList<ServiceInstanceEntry> getRunningLiveInstances() throws DeploymentException {
		try {
			ArrayList<ServiceInstanceEntry> results = new ArrayList<ServiceInstanceEntry>();
			// submit the request
			WebTarget path = baseWebTarget.path("instancedata/serviceInstances");
			Builder request = path.request().accept(MediaType.APPLICATION_XML_TYPE);
			ServiceInstanceList serviceInstanceList = request.get().readEntity(ServiceInstanceList.class);
			for (SimpleXLink link : serviceInstanceList.getLinks()) {
				WebTarget target = client.target(link.getHref());
				ServiceInstanceEntry serviceInstanceEntry = target.request().accept(MediaType.APPLICATION_XML_TYPE)
						.get(ServiceInstanceEntry.class);
				results.add(serviceInstanceEntry);
			}
			return results;
		} catch (ProcessingException e) {
			LOGGER.warn("Failed to get running InstancesLiveList - Server was not reachable.", e);
			throw new DeploymentException(
					"Failed to get running InstancesLiveList - OpenTOSCA Server was not reachable");
		}
	}

	/**
	 * Gets all deployed CSARs
	 * 
	 * @return list of deployed csars
	 * @throws DeploymentException
	 */
	public ArrayList<org.opentosca.csarrepo.util.jaxb.SimpleXLink> getDeployedCsars() throws DeploymentException {
		try {
			WebTarget path = baseWebTarget.path("CSARs");
			Builder request = path.request();
			References references = request.get().readEntity(References.class);

			ArrayList<org.opentosca.csarrepo.util.jaxb.SimpleXLink> results = new ArrayList<org.opentosca.csarrepo.util.jaxb.SimpleXLink>();
			results.addAll(references.getLinks());
			return results;
		} catch (ProcessingException e) {
			LOGGER.warn("Failed to get deployed CSARs - Server was not reachable.", e);
			throw new DeploymentException("Failed to get deployed CSARs - OpenTOSCA Server was not reachable");
		}
	}
}
