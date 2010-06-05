package com.dominikdorn.rest.listeners;

import com.dominikdorn.rest.registration.ClientRegistry;
import com.dominikdorn.rest.registration.ClientRegistryImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class ClientRegistryListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ClientRegistry registry = new ClientRegistryImpl();
        sce.getServletContext().setAttribute("clientRegistry", registry);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("clientRegistry");
    }
}
