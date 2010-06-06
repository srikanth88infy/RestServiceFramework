package com.dominikdorn.rest.metaservice.indexgateway.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


import com.dominikdorn.rest.marshalling.Marshaller;
import com.dominikdorn.rest.metaservice.indexgateway.lookup.ChildLookupThread;
import com.dominikdorn.rest.registration.ClientRegistry;
import com.dominikdorn.rest.services.EncodingNegotiator;

/**
 * Starts the child lookup thread for the central index.
 * 
 * @author peter
 * 
 */
public class ChildLookupListener implements ServletContextListener {

	private String ownAddr;

	private String ownPort;

	private ChildLookupThread clt;

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		this.clt.setPing(false);

	}

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		final ClientRegistry registry = (ClientRegistry) sce.getServletContext().getAttribute("clientRegistry");
		this.ownAddr = (String) System.getProperties().get("metaService.internal.host");
		this.ownPort = (String) System.getProperties().get("metaService.internal.port");
		
		if (this.ownAddr == null)
			throw new RuntimeException(
					"required System-Property metaService.internal.host not defined");

		if (this.ownPort == null)
			throw new RuntimeException(
					"required System-Property metaService.internal.port not defined");
		
		if (registry == null) 
			throw new RuntimeException("Could not obtain client registry!");
		
		this.clt = new ChildLookupThread();
		this.clt.setAddress(ownAddr);
		this.clt.setPort(ownPort);
		this.clt.setRegistry(registry);
		
		this.clt.start();
	}

}
