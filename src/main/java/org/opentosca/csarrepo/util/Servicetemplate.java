package org.opentosca.csarrepo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Servicetemplate {
	private String id;
	private String namespace;
	private String name;
	
	public Servicetemplate(String id, String namespace, String name) {
		this.id = id;
		this.name = name;
		this.namespace = namespace;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getNamespace() {
		return this.namespace;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getWineryAddress() {
		String tmpNamespace = "";
		String tmpId = "";
		try {
			tmpNamespace = URLEncoder.encode(this.namespace, "UTF-8");
			 tmpId = URLEncoder.encode(this.id, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		return tmpNamespace + "/" + tmpId + "/";
	}

}
