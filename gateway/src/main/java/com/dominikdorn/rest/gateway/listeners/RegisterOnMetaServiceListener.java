package com.dominikdorn.rest.gateway.listeners;

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
public class RegisterOnMetaServiceListener implements ServletContextListener{
    String ownInternalAddress;
    String ownInternalPort;
    String ownExternalAddress;
    String ownExternalPort;
    String parentAddress;
    String parentPort;


    @Override
    public void contextInitialized(ServletContextEvent sce) {


        ownInternalAddress = (String) System.getProperties().get("gateway.internal.host");
        ownInternalPort = (String) System.getProperties().get("gateway.internal.port");

        ownExternalAddress = (String) System.getProperties().get("gateway.external.host");
        ownExternalPort = (String) System.getProperties().get("gateway.external.port");

        parentAddress = (String) System.getProperties().get("metaService.host");

        parentPort = (String) System.getProperties().get("metaService.port");

        if(ownInternalAddress == null)
            throw new RuntimeException("required System-Property gateway.internal.host not defined");

        if(ownInternalPort == null)
            throw new RuntimeException("required System-Property gateway.internal.port not defined");

        if(ownExternalAddress == null)
            throw new RuntimeException("required System-Property gateway.external.host not defined");

        if(ownExternalPort == null)
            throw new RuntimeException("required System-Property gateway.external.port not defined");

        if(parentAddress == null)
            throw new RuntimeException("required System-Property metaService.host not defined");

        if(parentPort == null)
            throw new RuntimeException("required System-Property metaService.port not defined");

        System.out.println("ownInternalAddress = " + ownInternalAddress);
        System.out.println("ownInternalPort = " + ownInternalPort);
        System.out.println("ownExternalAddress = " + ownExternalAddress);
        System.out.println("ownExternalPort = " + ownExternalPort);
        System.out.println("metaServiceAddress = " + parentAddress);
        System.out.println("metaServicePort = " + parentPort);

        RegistrationThread thread = new RegistrationThread(2000, -1, parentAddress, parentPort,  ownExternalAddress, ownExternalPort);
        thread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        RegistrationService service = new RegistrationServiceImpl();
        service.unRegisterOnParent(parentAddress, parentPort, ownExternalAddress, ownExternalPort);
    }
}