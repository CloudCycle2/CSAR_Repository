package org.opentosca.csarrepo.rest.resource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.rest.model.CsarListEntry;
import org.opentosca.csarrepo.rest.model.SimpleXLink;
import org.opentosca.csarrepo.rest.util.LinkBuilder;
import org.opentosca.csarrepo.service.CreateCsarService;
import org.opentosca.csarrepo.service.ListCsarService;

public class CsarListResource {

	private static final Logger LOGGER = LogManager.getLogger(CsarListResource.class);

	UriInfo uriInfo;

	public CsarListResource(UriInfo uriInfo) {
		this.uriInfo = uriInfo;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getCsars() {
		List<SimpleXLink> links = new LinkedList<SimpleXLink>();
		links.add(LinkBuilder.selfLink(uriInfo));

		// TODO: add content, create own Entry-Type
		ListCsarService listService = new ListCsarService(0L);
		if (listService.hasErrors()) {
			// TODO: log errors
			return Response.serverError().entity("Error listing all csars:" + listService.getErrors().get(0)).build();
		}
		List<Csar> result = listService.getResult();
		List<SimpleXLink> csarLinks = new ArrayList<SimpleXLink>();
		for (Csar csar : result) {
			csarLinks.add(new SimpleXLink(LinkBuilder.linkToCsar(uriInfo, csar.getId()), csar.getName()));
		}

		CsarListEntry csarListEntry = new CsarListEntry(links, csarLinks);
		return Response.ok(csarListEntry).build();
	}

	// TODO: move id to constant class
	@Path("/{" + "id" + "}")
	public Object getCsar(@PathParam("id") long id, @Context UriInfo uriInfo) {
		return new CsarResource(uriInfo, id);
	}

	@POST
	@Produces(MediaType.APPLICATION_XML)
	public Response createCsar(@QueryParam("csar_name") String csarName) {
		// TODO: add real UserID
		long userID = 0L;
		CreateCsarService createCsarService = new CreateCsarService(userID, csarName);

		if (createCsarService.hasErrors()) {
			// TODO: error handling (not only first)
			LOGGER.error(createCsarService.getErrors().get(0));
			return null;
		}

		long csarID = createCsarService.getResult();

		return Response.created(LinkBuilder.linkToCsar(uriInfo, csarID)).build();
	}
}
