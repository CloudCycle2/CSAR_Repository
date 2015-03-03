package org.opentosca.csarrepo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.opentosca.csarrepo.filesystem.FileSystem;
import org.opentosca.csarrepo.model.CsarFile;


public class WineryApiClient {

	private static final Logger LOGGER = LogManager.getLogger(WineryApiClient.class);
	
	private Client client;
	private String url;

	public WineryApiClient(URL url) {
		this.url = url.toExternalForm();
		if (this.url.charAt(this.url.length() - 1) != '/') {
			this.url += "/";
		}
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
		WebTarget target = client.target(this.url);
		Builder request = target.request();
		Response response = request.post(entity);

		// handle response
		if (Status.NO_CONTENT.getStatusCode() == response.getStatus()) {
			return;
		}

		throw new Exception("failed to push to winery");
	}

	public InputStream pullFromWinery(String id) throws Exception {
		// send request
		WebTarget target = client.target(this.url + "servicetemplates/" + id);
		Builder request = target.request();
		request.accept("application/zip");
		Response response = request.get();

		if (Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
			// 404
			throw new Exception("No corresponding servicetemplate found");
		}

		if (Status.OK.getStatusCode() == response.getStatus()) {
			// 200
			try {
				InputStream stream = (InputStream) response.getEntity();
				return stream;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		// other status code
		throw new Exception("Error connecting to winery");
	}

	public List<Servicetemplate> getServiceTemplates() throws Exception {
		// send request
		WebTarget target = client.target(this.url + "servicetemplates/");
		Builder request = target.request();
		request.accept("application/json");
		Response response = request.get();

		if (Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
			// 404
			throw new Exception("Invalid call to winery");
		}
		
		if(Status.OK.getStatusCode() == response.getStatus()) {
			// 200
			String json = response.readEntity(String.class);
			return this.parseServicetemplateJsonToList(json);
		}

		throw new Exception("Error connecting to winery");
	}
	
	private List<Servicetemplate> parseServicetemplateJsonToList(String json) {
		JSONArray jsonArray;
		
		List<Servicetemplate> result = new ArrayList<Servicetemplate>();
		try {
			jsonArray = new JSONArray(json);
			
			for(int i = 0; i < jsonArray.length(); i++) {
				String tmpId = jsonArray.getJSONObject(i).getString("id");
				String tmpNamespace = jsonArray.getJSONObject(i).getString("namespace");
				String tmpName = jsonArray.getJSONObject(i).getString("name");
				
				result.add(new Servicetemplate(tmpId, tmpNamespace, tmpName));
			}
		} catch (JSONException e) {
			LOGGER.error(e);
			return null;
		}
		
		return result;
	}
}
