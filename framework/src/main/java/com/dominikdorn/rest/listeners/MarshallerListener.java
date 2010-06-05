package com.dominikdorn.rest.listeners;

import com.dominikdorn.rest.marshalling.JsonMarshallingStrategy;
import com.dominikdorn.rest.marshalling.Marshaller;
import com.dominikdorn.rest.marshalling.MarshallingStrategy;
import com.dominikdorn.rest.marshalling.XmlMarshallingStrategy;
import com.dominikdorn.rest.services.OutputType;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class MarshallerListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JsonMarshallingStrategy jsonMarshallingStrategy = new JsonMarshallingStrategy();
        XmlMarshallingStrategy xmlMarshallingStrategy = new XmlMarshallingStrategy();

        Map<OutputType, MarshallingStrategy> strategies = new HashMap<OutputType, MarshallingStrategy>();
        strategies.put(OutputType.JSON, jsonMarshallingStrategy);
        strategies.put(OutputType.XML, xmlMarshallingStrategy);
        Marshaller marshaller = new Marshaller(strategies, OutputType.JSON);

        sce.getServletContext().setAttribute("restMarshaller", marshaller);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
