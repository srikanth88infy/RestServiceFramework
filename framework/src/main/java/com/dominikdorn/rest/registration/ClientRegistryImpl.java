package com.dominikdorn.rest.registration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class ClientRegistryImpl implements ClientRegistry {

    private final TreeSet<String> clients = new TreeSet<String>();


    @Override
    public List<String> getClients() {
        List<String> result = new ArrayList<String>();
        result.addAll(clients);
        return result;
    }

    @Override
    public void addClient(String host, String port) {
        // TODO check synchronization here; is this done correctly?
        synchronized (clients)
        {
            clients.add(host + ":" + port);
        }
    }

    @Override
    public void removeClient(String host, String port) {
        // TODO check synchronization here; is this done correctly?
        synchronized (clients)
        {
            clients.remove(host + ":" + port);
        }
    }
}
