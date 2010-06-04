package com.dominikdorn.rest.gateway.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RegisterOnMetaServiceListener implements ServletContextListener{
    String ownInternalAddress;
    String ownInternalPort;
    String ownExternalAddress;
    String ownExternalPort;
    String metaServiceAddress;
    String metaServicePort;


    @Override
    public void contextInitialized(ServletContextEvent sce) {


        ownInternalAddress = (String) System.getProperties().get("gateway.internal.host");
        ownInternalPort = (String) System.getProperties().get("gateway.internal.port");

        ownExternalAddress = (String) System.getProperties().get("gateway.external.host");
        ownExternalPort = (String) System.getProperties().get("gateway.external.port");

        metaServiceAddress = (String) System.getProperties().get("metaService.host");

        metaServicePort = (String) System.getProperties().get("metaService.port");

        if(ownInternalAddress == null)
            throw new RuntimeException("required System-Property gateway.internal.host not defined");

        if(ownInternalPort == null)
            throw new RuntimeException("required System-Property gateway.internal.port not defined");

        if(ownExternalAddress == null)
            throw new RuntimeException("required System-Property gateway.external.host not defined");

        if(ownExternalPort == null)
            throw new RuntimeException("required System-Property gateway.external.port not defined");

        if(metaServiceAddress == null)
            throw new RuntimeException("required System-Property metaService.host not defined");

        if(metaServicePort == null)
            throw new RuntimeException("required System-Property metaService.port not defined");

        System.out.println("ownInternalAddress = " + ownInternalAddress);
        System.out.println("ownInternalPort = " + ownInternalPort);
        System.out.println("ownExternalAddress = " + ownExternalAddress);
        System.out.println("ownExternalPort = " + ownExternalPort);
        System.out.println("metaServiceAddress = " + metaServiceAddress);
        System.out.println("metaServicePort = " + metaServicePort);

        // TODO Spawn thread, connecting to gatewayAddress:gatewayPort for registration

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //To change body of implemented methods use File | Settings | File Templates.
        // TODO connect to gatewayAddress:gatewayPort and unregister
    }
}