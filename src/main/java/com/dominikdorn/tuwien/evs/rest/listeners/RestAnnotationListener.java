package com.dominikdorn.tuwien.evs.rest.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RestAnnotationListener implements ServletContextListener {
    /**
     * * Notification that the web application initialization
     * * process is starting.
     * * All ServletContextListeners are notified of context
     * * initialization before any filter or servlet in the web
     * * application is initialized.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // do classpath scanning
        // see http://bill.burkecentral.com/2008/01/14/scanning-java-annotations-at-runtime/
        // see http://java.sun.com/j2se/1.5.0/docs/guide/language/annotations.html
        
    }

    /**
     * * Notification that the servlet context is about to be shut down.
     * * All servlets and filters have been destroy()ed before any
     * * ServletContextListeners are notified of context
     * * destruction.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
