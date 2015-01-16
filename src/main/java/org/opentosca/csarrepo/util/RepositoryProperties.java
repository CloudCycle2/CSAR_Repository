package org.opentosca.csarrepo.util;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class RepositoryProperties implements ServletContextListener {

	private static final Logger LOGGER = LogManager.getLogger(RepositoryProperties.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext servletContext = event.getServletContext();
			InputStream propertiesStream = servletContext.getResourceAsStream("WEB-INF/repository.properties");

			if (propertiesStream == null) {
				LOGGER.error("Properties file does not exist. Create "
						+ servletContext.getRealPath("WEB-INF/repository.properties"));

				throw new RuntimeException("Property file does not exist. See log for more information.");
			}

			System.getProperties().load(propertiesStream);
		} catch (IOException e) {
			LOGGER.error("error while reading properties file " + e.getMessage());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		return;
	}
}
