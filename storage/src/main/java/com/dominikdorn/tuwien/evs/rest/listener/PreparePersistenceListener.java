package com.dominikdorn.tuwien.evs.rest.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class PreparePersistenceListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String ownAddress = (String) System.getProperties().get("storage.host");
        String ownPort = (String) System.getProperties().get("storage.port");

        String schemaName = "db_" + ownAddress.replace(".", "_")+"_"+ownPort;

        Map<String,String> persistenceProperties = new HashMap<String,String>();
        persistenceProperties.put("eclipselink.session.customizer", "com.dominikdorn.tuwien.evs.rest.eclipselink.StorageSchemaSessionCustomizer");
        persistenceProperties.put("schema.name", schemaName);

        
        sce.getServletContext().setAttribute("persistenceProperties", persistenceProperties);
        sce.getServletContext().setAttribute("restSchemaName", schemaName);


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
