package by.bsuir.tunnels.model;

import org.apache.log4j.Logger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Tunnel{

    private static Logger log = Logger.getLogger(Tunnel.class);

    private final static int POOL_SIZE = 1;
    private int number;
    private Semaphore semaphore;

    public Tunnel(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public boolean getTunnelAsResource(long maxWatSeconds) {

        boolean isUsed = false;
        try {
            isUsed = semaphore.tryAcquire(maxWatSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        return isUsed;
    }

}
