package com.dominikdorn.rest.listeners;

import com.dominikdorn.rest.services.EncodingNegotiator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 *
 *
 * Initializes the EncodingNegotiator and registeres it in the servlet Context.
 */
public class EncodingNegotiatorListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EncodingNegotiator negotiator = new EncodingNegotiator();
        sce.getServletContext().setAttribute("restEncodingNegotiator", negotiator);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("restEncodingNegotiator");
    }
}
