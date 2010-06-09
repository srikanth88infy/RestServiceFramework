package com.dominikdorn.rest.listeners;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class PersistenceListener implements ServletContextListener {

    EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        System.out.println("creating persistence: EntityManagerFactory");

        Map<String,String> persistenceProperties = (Map<String, String>) sce.getServletContext().getAttribute("persistenceProperties");

        if(persistenceProperties == null)
        {
//            System.out.println("No persistenceProperties defined in the servlet context, using the default ones");
            persistenceProperties = new HashMap<String,String>();
        }
        emf = Persistence.createEntityManagerFactory("RestPersistenceUnit",persistenceProperties);
        
        sce.getServletContext().setAttribute("restEMF", emf);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        emf.close();
        emf = null;
        sce.getServletContext().removeAttribute("restEMF");
    }
}
