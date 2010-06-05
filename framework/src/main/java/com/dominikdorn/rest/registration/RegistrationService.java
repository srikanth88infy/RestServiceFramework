package com.dominikdorn.rest.registration;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public interface RegistrationService {

    /**
     * Registers a client on its parent.
     * Returns false if the parent is not online or returns the wrong result.
     * @param parentHost
     * @param parentPort
     * @param myHost
     * @param myPort
     * @return true on success, false otherwise
     * @throws RegisteringException if a Exception occurred
     */
    public boolean registerOnParent(String parentHost, String parentPort, String myHost, String myPort) throws RegisteringException;

    /**
     * Un-registers a client from its parent.
     * Fire &amp; Forget Pattern
     * @param parentHost
     * @param parentPort
     * @param myHost
     * @param myPort
     */
    public void unRegisterOnParent(String parentHost, String parentPort, String myHost, String myPort);

}
