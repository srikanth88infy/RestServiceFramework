package com.dominikdorn.rest.metaService.centralIndex.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RegisterOnMetaServiceGatewayListener implements ServletContextListener{
    String ownAddress;
    String ownPort;
    String gatewayAddress;
    String gatewayPort;


    @Override
    public void contextInitialized(ServletContextEvent sce) {


        ownAddress = (String) System.getProperties().get("centralIndex.host");

        ownPort = (String) System.getProperties().get("centralIndex.port");

        gatewayAddress = (String) System.getProperties().get("gateway.host");

        gatewayPort = (String) System.getProperties().get("gateway.port");

        if(ownAddress == null)
            throw new RuntimeException("required System-Property centralIndex.host not defined");

        if(ownPort == null)
            throw new RuntimeException("required System-Property centralIndex.port not defined");

        if(gatewayAddress == null)
            throw new RuntimeException("required System-Property gateway.host not defined");

        if(gatewayPort == null)
            throw new RuntimeException("required System-Property gateway.port not defined");

        System.out.println("ownAddress = " + ownAddress);
        System.out.println("ownPort = " + ownAddress);

        System.out.println("gatewayAddress = " + gatewayAddress);
        System.out.println("gatewayPort = " + gatewayPort);


        // TODO Spawn thread, connecting to gatewayAddress:gatewayPort for registration

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //To change body of implemented methods use File | Settings | File Templates.
        // TODO connect to gatewayAddress:gatewayPort and unregister
    }
}
