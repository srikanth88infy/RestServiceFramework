package com.dominikdorn.tuwien.evs.rest.listener;

import com.dominikdorn.rest.registration.RegistrationService;
import com.dominikdorn.rest.registration.RegistrationServiceImpl;
import com.dominikdorn.rest.registration.RegistrationThread;

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
    String parentAddress;
    String parentPort;


    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ownAddress = (String) System.getProperties().get("storage.host");
        ownPort = (String) System.getProperties().get("storage.port");
        parentAddress = (String) System.getProperties().get("locationIndex.host");
        parentPort = (String) System.getProperties().get("locationIndex.port");

        if(ownAddress == null)
            throw new RuntimeException("required System-Property storage.host not defined");

        if(ownPort == null)
            throw new RuntimeException("required System-Property storage.port not defined");

        if(parentAddress == null)
            throw new RuntimeException("required System-Property locationIndex.host not defined");

        if(parentPort == null)
            throw new RuntimeException("required System-Property locationIndex.port not defined");

        System.out.println("ownAddress = " + ownAddress);
        System.out.println("ownPort = " + ownAddress);

        System.out.println("locationIndexAddress = " + parentAddress);
        System.out.println("locationIndexPort = " + parentPort);

        RegistrationThread thread = new RegistrationThread(2000, -1, parentAddress, parentPort, ownAddress, ownPort);
        thread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        RegistrationService service = new RegistrationServiceImpl();
        service.unRegisterOnParent(parentAddress, parentPort, ownAddress, ownPort);
    }
}
