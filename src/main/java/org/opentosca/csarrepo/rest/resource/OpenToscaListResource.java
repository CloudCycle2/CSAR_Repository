package org.opentosca.csarrepo.rest.resource;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.rest.model.RootEntry;
import org.opentosca.csarrepo.rest.model.SimpleXLink;
import org.opentosca.csarrepo.rest.util.LinkBuilder;

public class OpenToscaListResource {

	private static final Logger LOGGER = LogManager.getLogger(OpenToscaListResource.class);

	UriInfo uriInfo;

	public OpenToscaListResource(UriInfo uriInfo) {
		this.uriInfo = uriInfo;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getCsars() {
		List<SimpleXLink> links = new LinkedList<SimpleXLink>();
		links.add(LinkBuilder.selfLink(uriInfo));

		// TODO: add content, create own Entry-Type
		RootEntry rootEntry = new RootEntry(links);

		return Response.ok(rootEntry).build();
	}

}
