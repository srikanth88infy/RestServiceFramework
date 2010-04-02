package com.dominikdorn.rest.listeners;

import com.dominikdorn.rest.invoking.Invoker;
import com.dominikdorn.rest.marshalling.JsonMarshallingStrategy;
import com.dominikdorn.rest.marshalling.Marshaller;
import com.dominikdorn.rest.marshalling.MarshallingStrategy;
import com.dominikdorn.rest.marshalling.XmlMarshallingStrategy;
import com.dominikdorn.rest.services.AnnotationScanner;
import com.dominikdorn.rest.services.ObjectRegistry;
import com.dominikdorn.rest.services.OutputType;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class ApplicationStartupListener implements ServletContextListener {



    /**
     * * Notification that the web application initialization
     * * process is starting.
     * * All ServletContextListeners are notified of context
     * * initialization before any filter or servlet in the web
     * * application is initialized.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AnnotationScanner scanner = new AnnotationScanner();
        ObjectRegistry registry = new ObjectRegistry();

        Map<String, Class> objectDatabase = scanner.getRegisteredObjectMap(getWebInfClassesURL(sce.getServletContext()));
        registry.setObjectDatabase(objectDatabase);
        for(Class clazz : objectDatabase.values())
        {
            for( String field : scanner.getSearchableFieldsForClass(clazz))
            {
                registry.addSearchableField(clazz, field);
            }
        }

        // creating marshalling-strategies
        JsonMarshallingStrategy jsonMarshallingStrategy = new JsonMarshallingStrategy();
        XmlMarshallingStrategy xmlMarshallingStrategy = new XmlMarshallingStrategy();

        Map<OutputType, MarshallingStrategy> strategies = new HashMap<OutputType, MarshallingStrategy>();
        strategies.put(OutputType.JSON, jsonMarshallingStrategy);
        strategies.put(OutputType.XML, xmlMarshallingStrategy);


        // creating marshaller
        Marshaller marshaller = new Marshaller(strategies, OutputType.JSON);

        Invoker invoker = new Invoker(marshaller);


        // registers the registry & the invoker in application scope
        sce.getServletContext().setAttribute("restObjectRegistry", registry);
        sce.getServletContext().setAttribute("restInvoker", invoker);
    }

    public URL getWebInfClassesURL(ServletContext context) {
        Set objs = context.getResourcePaths("/WEB-INF/classes/");

        for (Object o : objs) {
            try {
                URL url = context.getResource(o.toString());
                String urlString = url.toString();
                int index = urlString.lastIndexOf("/WEB-INF/classes/");
                urlString = urlString.substring(0, index + "/WEB-INF/classes/".length());
                return new URL(urlString);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


    /**
     * * Notification that the servlet context is about to be shut down.
     * * All requestHandler and filters have been destroy()ed before any
     * * ServletContextListeners are notified of context
     * * destruction.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
