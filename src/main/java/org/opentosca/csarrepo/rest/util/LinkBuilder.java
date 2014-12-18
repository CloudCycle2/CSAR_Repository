package org.opentosca.csarrepo.rest.util;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.opentosca.csarrepo.rest.model.SimpleXLink;
import org.opentosca.csarrepo.rest.resource.CsarListResource;
import org.opentosca.csarrepo.rest.resource.CsarResource;
import org.opentosca.csarrepo.rest.resource.RootResource;

public class LinkBuilder {

	public static SimpleXLink selfLink(UriInfo uriInfo) {
		return new SimpleXLink(uriInfo.getAbsolutePath().toString(), "self");
	}

	public static URI linkToCsarList(UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder().path(RootResource.class)
				.path(RootResource.class, "getCsars").build(new Object[0]);
	}

	public static URI linkToWineryList(UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder().path(RootResource.class)
				.path(RootResource.class, "getWineries").build(new Object[0]);
	}

	public static URI linkToOpenToscaList(UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder().path(RootResource.class)
				.path(RootResource.class, "getOpenToscas").build(new Object[0]);
	}

	public static URI linkToCsar(UriInfo uriInfo, long id) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", Long.toString(id));
		return UriBuilder.fromUri(linkToCsarList(uriInfo))
				.path(CsarListResource.class, "getCsar").buildFromMap(paramMap);
	}

	public static URI linkToCsarFile(UriInfo uriInfo, long csarId,
			long csarFileId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", Long.toString(csarFileId));
		return UriBuilder.fromUri(linkToCsar(uriInfo, csarId))
				.path(CsarResource.class, "getCsarFile").buildFromMap(paramMap);
	}

}
