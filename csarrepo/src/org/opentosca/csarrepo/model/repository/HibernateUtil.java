package org.opentosca.csarrepo.model.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Helper class for Hibernate related operations
 * 
 * generates sessionFactory once and returns newly generated sessions for it
 * 
 * @author eiselems (marcus.eisele@gmail.com)
 *
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	static {
		Configuration configuration = new Configuration();
		configuration.configure();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	/**
	 * Returns a newly generated session
	 * The session has to <b>closed</b> after finishing the queries
	 * @return a new session created by the underlying serviceFactory
	 */
	protected static Session getSession() {
		return sessionFactory.openSession();
	}
}
