package org.opentosca.csarrepo.rest.resource;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.rest.model.RootEntry;
import org.opentosca.csarrepo.rest.model.SimpleXLink;
import org.opentosca.csarrepo.rest.util.LinkBuilder;

@Path("")
public class RootResource {

	private static final Logger LOGGER = LogManager.getLogger(RootResource.class);

	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getRoot() {
		List<SimpleXLink> links = new LinkedList<SimpleXLink>();
		links.add(LinkBuilder.selfLink(uriInfo));
		links.add(new SimpleXLink(LinkBuilder.linkToCsarList(uriInfo), "csars"));
		links.add(new SimpleXLink(LinkBuilder.linkToWineryList(uriInfo), "winery"));
		links.add(new SimpleXLink(LinkBuilder.linkToOpenToscaList(uriInfo), "opentosca"));

		RootEntry rootEntry = new RootEntry(links);

		return Response.ok(rootEntry).build();
	}

	@Path("/csar")
	public Object getCsars() {
		return new CsarListResource(uriInfo);
	}

	@Path("/winery")
	public Object getWineries() {
		return new WineryListResource(uriInfo);
	}

	@Path("/opentosca")
	public Object getOpenToscas() {
		return new OpenToscaListResource(uriInfo);
	}

}
