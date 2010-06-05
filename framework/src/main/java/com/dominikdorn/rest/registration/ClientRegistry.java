package com.dominikdorn.rest.registration;

import java.util.List;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public interface ClientRegistry {

    /**
     * Returns a List with the addresses of all Clients.
     * Every Entry has the form (hostname/ip):(port)
     * @return
     */
    public List<String> getClients();


    /**
     * Adds a new Client to the list of managed Clients
     * @param host the hostname/ip of the client
     * @param port the port used by the client
     */
    public void addClient(String host, String port);

    /**
     * Removes a Client from the list of managed Clients
     * @param host the hostname/ip of the client
     * @param port the port used by the client
     */
    public void removeClient(String host, String port);

}
