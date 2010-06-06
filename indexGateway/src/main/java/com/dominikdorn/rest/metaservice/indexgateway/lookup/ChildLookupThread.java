package com.dominikdorn.rest.metaservice.indexgateway.lookup;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.dominikdorn.rest.registration.ClientRegistry;

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
                boolean b = this.ping(c);
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

//    public void setMarshaller(final Marshaller marshaller) {
//        this.marshaller = marshaller;
//    }
//
//    public void setNegotiator(final EncodingNegotiator negotiator) {
//        this.negotiator = negotiator;
//    }
    
    private boolean ping(final String addr) {
        final HttpClient client = new DefaultHttpClient();
        final HttpGet get = new HttpGet("http://" + addr + "/ping");
        
        try {
            HttpResponse response = client.execute(get);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                return true;
            }
        } catch (final IOException e) {
            
        }
        
        return false;
        
    }


//    private List<String> getClients() {
//
//        final HttpClient client = new DefaultHttpClient();
//        final HttpGet get = new HttpGet("http://" + this.getAddress() + ":" + this.getPort() + "/clients");
//        final List<String> result = new ArrayList<String>();
//
//        try {
//            HttpResponse response = client.execute(get);
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                final StringBuffer resp = new StringBuffer();
//                final InputStream in = entity.getContent();
//                final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    resp.append(line);
//                }
//                reader.close();
//
//                System.out.println(resp.toString());
//                System.out.println(Arrays.deepToString(response.getAllHeaders()));
//
//                result.addAll((List<String>) this.marshaller.deSerialize(resp.toString(), List.class, OutputType.JSON));
//
//            }
//        } catch (IOException e) {
//            // what to do here?
//            // e.printStackTrace();
//        }
//
//        return result;
//    }
}
