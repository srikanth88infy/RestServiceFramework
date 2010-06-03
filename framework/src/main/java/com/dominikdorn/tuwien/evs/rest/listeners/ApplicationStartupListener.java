package com.dominikdorn.tuwien.evs.rest.listeners;

import com.dominikdorn.tuwien.evs.rest.converter.Converter;
import com.dominikdorn.tuwien.evs.rest.converter.ConverterService;
import com.dominikdorn.tuwien.evs.rest.interceptors.LoggingInterceptor;
import com.dominikdorn.tuwien.evs.rest.invoking.Invoker;
import com.dominikdorn.tuwien.evs.rest.invoking.InvokerImpl;
import com.dominikdorn.tuwien.evs.rest.marshalling.JsonMarshallingStrategy;
import com.dominikdorn.tuwien.evs.rest.marshalling.Marshaller;
import com.dominikdorn.tuwien.evs.rest.marshalling.MarshallingStrategy;
import com.dominikdorn.tuwien.evs.rest.marshalling.XmlMarshallingStrategy;
import com.dominikdorn.tuwien.evs.rest.services.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        scanner.initialize(getWebInfClassesURL(sce.getServletContext()));

        ObjectRegistry registry = new ObjectRegistry();

        ConverterService converterService = new ConverterService();

        Map<String, Class> objectDatabase = scanner.getRegisteredObjectMap();
        registry.setObjectDatabase(objectDatabase);
        for(Class clazz : objectDatabase.values())
        {
            for( String field : scanner.getSearchableFieldsForClass(clazz))
            {
                registry.addSearchableField(clazz, field);
            }
        }

        Map<Class, Converter> converters = scanner.getConverters();
        for(Map.Entry<Class,Converter> e : converters.entrySet())
        {
            converterService.registerConverter(e.getKey(), e.getValue());
        }

        // creating marshalling-strategies
        JsonMarshallingStrategy jsonMarshallingStrategy = new JsonMarshallingStrategy();
        XmlMarshallingStrategy xmlMarshallingStrategy = new XmlMarshallingStrategy();

        Map<OutputType, MarshallingStrategy> strategies = new HashMap<OutputType, MarshallingStrategy>();
        strategies.put(OutputType.JSON, jsonMarshallingStrategy);
        strategies.put(OutputType.XML, xmlMarshallingStrategy);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RestPersistenceUnit");


        // creating marshaller
        Marshaller marshaller = new Marshaller(strategies, OutputType.JSON);

        RestService serviceImpl = new RestServiceImpl(emf);
        RestService service = (RestService) LoggingInterceptor.getProxy( serviceImpl );


        Invoker invokerImpl = new InvokerImpl(marshaller, service, converterService);
        Invoker invoker = (Invoker) LoggingInterceptor.getProxy(invokerImpl);

        EncodingNegotiator negotiator = new EncodingNegotiator();


        // registers the registry & the invoker in application scope
        sce.getServletContext().setAttribute("restObjectRegistry", registry);
        sce.getServletContext().setAttribute("restInvoker", invoker);
        sce.getServletContext().setAttribute("restEncodingNegotiator", negotiator);
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
