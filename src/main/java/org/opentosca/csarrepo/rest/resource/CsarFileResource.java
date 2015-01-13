package org.opentosca.csarrepo.rest.resource;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentosca.csarrepo.filesystem.FileSystem;
import org.opentosca.csarrepo.model.Csar;
import org.opentosca.csarrepo.model.CsarFile;
import org.opentosca.csarrepo.rest.model.CsarFileEntry;
import org.opentosca.csarrepo.rest.model.SimpleXLink;
import org.opentosca.csarrepo.rest.util.LinkBuilder;
import org.opentosca.csarrepo.service.ShowCsarService;

public class CsarFileResource {

	private static final Logger LOGGER = LogManager.getLogger(CsarFileResource.class);
	private UriInfo uriInfo;
	private long csarId;
	private long id;

	public CsarFileResource(UriInfo uriInfo, long csarId, long id) {
		this.uriInfo = uriInfo;
		this.csarId = csarId;
		this.id = id;
	}

	/**
	 * @return Meta Data of the file
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getCsarFile() {
		// TODO: check if csarFile exists
		List<SimpleXLink> links = new LinkedList<SimpleXLink>();
		links.add(LinkBuilder.selfLink(uriInfo));
		links.add(new SimpleXLink(LinkBuilder.linkToCsar(uriInfo, csarId), "parent"));
		links.add(new SimpleXLink(LinkBuilder.linkToCsarFileDownloadFromCsarFile(uriInfo), "download"));

		// FIXME: use new ShowCsarFileService
		// TODO: use real userid
		ShowCsarService showService = new ShowCsarService(0L, csarId);

		if (showService.hasErrors()) {
			// TODO: move to helper
			// TODO: don't only fetch first error
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(showService.getErrors().get(0)).build();
		}

		Csar csar = showService.getResult();
		CsarFile matchedCsarFile = null;
		for (CsarFile csarFile : csar.getCsarFiles()) {
			if (id == csarFile.getId()) {
				matchedCsarFile = csarFile;
				break;
			}
			;
		}

		CsarFileEntry csarFileEntry = new CsarFileEntry(matchedCsarFile, links);

		// TODO: implement
		System.out.println("CsarFileResource.getCsarFile() id: " + id);
		return Response.ok(csarFileEntry).build();
	}

	@GET
	@Produces("application/csar")
	@Path("/download")
	public Response getFile() {

		FileSystem fs = new FileSystem();

		ShowCsarService showService = new ShowCsarService(0L, csarId);

		if (showService.hasErrors()) {
			// TODO: move to helper
			// TODO: don't only fetch first error
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(showService.getErrors().get(0)).build();
		}

		Csar csar = showService.getResult();
		CsarFile matchedCsarFile = null;
		for (CsarFile csarFile : csar.getCsarFiles()) {
			if (id == csarFile.getId()) {
				matchedCsarFile = csarFile;
				break;
			}
		}

		// TODO: check if it would be better if getFile gets a String
		File file = fs.getFile(UUID.fromString(matchedCsarFile.getHashedFile().getFilename()));
		String csarName = matchedCsarFile.getCsar().getName();
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=" + csarName + ".csar");
		return response.build();
	}

}
