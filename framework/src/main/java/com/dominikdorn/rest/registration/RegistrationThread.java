package com.dominikdorn.rest.registration;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class RegistrationThread extends Thread {

    int waitTime = 2000;
    int retries = 0;
    boolean shouldBeRunning = true;

    String parentHost;String parentPort; String myHost; String myPort;

    RegistrationService service = new RegistrationServiceImpl();

    /**
     * Creates a registrationThread that will try to register on the parent.
     * If a request fails, it will wait <code>waitTime</code> milliseconds and retries for
     * <code>retries</code> times.
     * @param waitTime milliseconds to wait between requests
     * @param retries amount of retries, -1 (or any negative number) for unlimited
     */
    public RegistrationThread(int waitTime, int retries, String parentHost, String parentPort, String myHost, String myPort) {
        this.waitTime = waitTime;
        this.retries = retries;
        this.parentHost = parentHost;
        this.parentPort = parentPort;
        this.myHost = myHost;
        this.myPort = myPort;
    }

    @Override
    public void run() {

        while(shouldBeRunning)
        {
            boolean result;
            try {
                result = service.registerOnParent(parentHost, parentPort, myHost, myPort);
                if(result)
                {
                    shouldBeRunning = false;
                    break;
                }
            } catch (RegisteringException e) {
                e.printStackTrace();
            }
            if(retries > 0)
            {
                retries--;
            }
            else if ( retries == 0)
            {
                shouldBeRunning = false;
            }
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }


        }




    }
}
