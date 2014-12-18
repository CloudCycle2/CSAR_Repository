package org.opentosca.csarrepo.rest.util;

import javax.ws.rs.core.UriInfo;

import org.opentosca.csarrepo.rest.model.SimpleXLink;

public class LinkBuilder {

	public static SimpleXLink selfLink(UriInfo uriInfo) {
		return new SimpleXLink(uriInfo.getAbsolutePath().toString(), "self");
	}
}
