package com.dominikdorn.rest.metaservice.indexgateway.lookup;

import java.util.List;

import com.dominikdorn.rest.registration.ClientRegistry;
import com.dominikdorn.rest.utils.Utilities;

/**
 * This thread gets all registered clients and pings them. If no valid response
 * comes back, then the clients are removed.
 * 
 * @author peter
 * 
 */
public class ChildLookupThread extends Thread {

    private boolean ping;

    private String address;

    private String port;

    private ClientRegistry registry;

    public ChildLookupThread() {
        super("ChildLookup");
        this.ping = true;
    }

    public ChildLookupThread(final String name) {
        super(name);
        this.ping = true;
    }

    @Override
    public void run() {
        if (this.getAddress() == null || this.getPort() == null) {
            // don't start the thread
            this.ping = false;
        }

        while (this.ping) {
            System.out.println("ping clients...");
            final List<String> clients = this.registry.getClients();

            for (final String c : clients) {
                boolean b = Utilities.ping(c);
                if (!b) {
                    System.out.println(c + " is down, unregistering");
                    this.registry.removeClient(c.substring(0, c.indexOf(':')), c.substring(c.indexOf(':') + 1));
                }
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // do nothing;
            }
        }

    }

    public void setPing(boolean ping) {
        this.ping = ping;
    }

    public boolean isPing() {
        return ping;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setRegistry(ClientRegistry registry) {
        this.registry = registry;

    }
}
