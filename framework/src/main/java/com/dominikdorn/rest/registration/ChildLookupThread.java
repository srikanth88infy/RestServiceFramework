package com.dominikdorn.rest.registration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dominikdorn.rest.utils.Utilities;

/**
 * This thread gets all registered clients and pings them. If no valid response
 * comes back, then the clients are removed and staged. The pinging continues,
 * however all staged clients are pinged three more times. If the staged client
 * does not return during that time, it is marked as dead and remains unregistered.
 * Otherwise it is registered again...
 * 
 * @author peter
 * 
 */
public class ChildLookupThread extends Thread {

    private boolean ping;

    private ClientRegistry registry;

    private Map<String, Integer> unreachable;

    public ChildLookupThread(final ClientRegistry reg) {
        super();
        this.ping = true;
        this.registry = reg;
        this.unreachable = new HashMap<String, Integer>();
    }

    @Override
    public void run() {
        if (this.registry == null) {
            // don't start the thread
            this.ping = false;
        }

        while (this.ping) {
            System.out.println("ping clients...");
            final List<String> clients = this.registry.getClients();

            for (final String c : clients) {
                boolean outthere = Utilities.ping(c);
                if (!outthere) {
                    if (this.unreachable.get(c) == null) {
                        System.out.println(c + " is down, putting in the staging");
                        this.unreachable.put(c, 0);
                        this.registry.removeClient(c.substring(0, c.indexOf(':')), c.substring(c.indexOf(':') + 1));
                    }
                }
            }

            for (String c : this.unreachable.keySet()) {
                boolean isback = Utilities.ping(c);

                if (isback) {
                    System.out.println("Client " + c + " is back!");
                    this.unreachable.remove(c);
                    this.registry.addClient(c.substring(0, c.indexOf(':')), c.substring(c.indexOf(':') + 1));
                } else {
                    if (this.unreachable.get(c) < 3) {
                        System.out.println(c + " is still down");
                        Integer count = this.unreachable.get(c);
                        this.unreachable.put(c, ++count);

                    } else {
                        System.out.println("Client " + c + " is dead, stop pinging");
                        this.unreachable.remove(c);
                    }
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

    public void setRegistry(ClientRegistry registry) {
        this.registry = registry;

    }
}
