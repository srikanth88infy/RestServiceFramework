package com.dominikdorn.rest.metaservice.indexgateway.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dominikdorn.rest.registration.ChildLookupThread;
import com.dominikdorn.rest.registration.ClientRegistry;

/**
 * Starts the child lookup thread for the central index.
 * 
 * @author peter
 * 
 */
public class MetaServiceChildLookupListener implements ServletContextListener {

	private ChildLookupThread clt;

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		this.clt.setPing(false);

	}

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		final ClientRegistry registry = (ClientRegistry) sce.getServletContext().getAttribute("clientRegistry");
		
		if (registry == null) 
			throw new RuntimeException("Could not obtain client registry!");
		
		this.clt = new ChildLookupThread(registry);
		this.clt.start();
	}

}
