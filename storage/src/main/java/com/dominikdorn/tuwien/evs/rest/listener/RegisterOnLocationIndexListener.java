package com.dominikdorn.tuwien.evs.rest.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RegisterOnLocationIndexListener implements ServletContextListener{
    String ownAddress;
    String ownPort;
    String locationIndexAddress;
    String locationIndexPort;


    @Override
    public void contextInitialized(ServletContextEvent sce) {


        ownAddress = (String) System.getProperties().get("storage.host");

        ownPort = (String) System.getProperties().get("storage.port");

        locationIndexAddress = (String) System.getProperties().get("locationIndex.host");

        locationIndexPort = (String) System.getProperties().get("locationIndex.port");

        if(ownAddress == null)
            throw new RuntimeException("required System-Property storage.host not defined");

        if(ownPort == null)
            throw new RuntimeException("required System-Property storage.port not defined");

        if(locationIndexAddress == null)
            throw new RuntimeException("required System-Property locationIndex.host not defined");

        if(locationIndexPort == null)
            throw new RuntimeException("required System-Property locationIndex.port not defined");

        System.out.println("ownAddress = " + ownAddress);
        System.out.println("ownPort = " + ownAddress);

        System.out.println("locationIndexAddress = " + locationIndexAddress);
        System.out.println("locationIndexPort = " + locationIndexPort);


        // TODO Spawn thread, connecting to localIndexAddress:localIndexPort for registration

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //To change body of implemented methods use File | Settings | File Templates.
        // TODO connect to localIndexAddress:localIndexPort and unregister
    }
}
