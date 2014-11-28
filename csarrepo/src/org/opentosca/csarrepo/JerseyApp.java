package org.opentosca.csarrepo;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 * Jersey starting point
 * 
 * used to register resources and features
 * 
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
public class JerseyApp extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		// register resources and features
		classes.add(MultiPartFeature.class);
		classes.add(MyResource.class);
		classes.add(LoggingFilter.class);
		return classes;
	}
}
