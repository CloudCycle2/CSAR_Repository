package org.opentosca.csarrepo.util;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class RepositoryProperties implements ServletContextListener {
	
	private static final Logger LOGGER = LogManager.getLogger(RepositoryProperties.class);
	
	public void contextInitialized(ServletContextEvent event) {
		try {
			InputStream propertiesStream = event.getServletContext().getResourceAsStream("WEB-INF/repository.properties");
			System.getProperties().load(propertiesStream);
		} catch (IOException e) {
			LOGGER.error("error while reading properties file " + e.getMessage());
		}
    }
    public void contextDestroyed(ServletContextEvent event) {
        return;
    }
}
