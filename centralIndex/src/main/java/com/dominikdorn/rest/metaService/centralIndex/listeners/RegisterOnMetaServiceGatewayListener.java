package com.dominikdorn.rest.metaService.centralIndex.listeners;

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
public class RegisterOnMetaServiceGatewayListener implements ServletContextListener{
    String ownAddress;
    String ownPort;
    String parentAddress;
    String parentPort;


    @Override
    public void contextInitialized(ServletContextEvent sce) {


        ownAddress = (String) System.getProperties().get("centralIndex.host");

        ownPort = (String) System.getProperties().get("centralIndex.port");

        parentAddress = (String) System.getProperties().get("gateway.host");

        parentPort = (String) System.getProperties().get("gateway.port");

        if(ownAddress == null)
            throw new RuntimeException("required System-Property centralIndex.host not defined");

        if(ownPort == null)
            throw new RuntimeException("required System-Property centralIndex.port not defined");

        if(parentAddress == null)
            throw new RuntimeException("required System-Property gateway.host not defined");

        if(parentPort == null)
            throw new RuntimeException("required System-Property gateway.port not defined");

        System.out.println("ownAddress = " + ownAddress);
        System.out.println("ownPort = " + ownAddress);

        System.out.println("gatewayAddress = " + parentAddress);
        System.out.println("gatewayPort = " + parentPort);


        RegistrationThread thread = new RegistrationThread(2000, -1, parentAddress, parentPort, ownAddress, ownPort);
        thread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        RegistrationService service = new RegistrationServiceImpl();
        service.unRegisterOnParent(parentAddress, parentPort, ownAddress, ownPort);
    }
}
