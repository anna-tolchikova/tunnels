package by.bsuir.tunnels.pool;

import by.bsuir.tunnels.exceptions.ResourceException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

// класс управляет доступом к тоннелям

public class TunnelsPool{
    private final static int POOL_SIZE = 2; // размер пула
    private Semaphore  semaphore;
    private LinkedList<Tunnel> resources;


    public TunnelsPool(){
        semaphore = new Semaphore(POOL_SIZE, true);
        resources = new LinkedList<Tunnel>();
    }

    public TunnelsPool(List<Tunnel> source) {
        semaphore = new Semaphore(POOL_SIZE, true);
        resources = new LinkedList<Tunnel>();
        resources.addAll(source);
    }
    public Tunnel getResource(int maxWaitSeconds, String site) throws ResourceException {
        try {
            if (semaphore.tryAcquire(maxWaitSeconds, TimeUnit.SECONDS)) {
                Tunnel res = resources.get(resources.size()-1);
                


                return res;
            }
        } catch (InterruptedException e) {
            throw new ResourceException(e);
        }
        return null;
    }

    public void returnResource(Tunnel res) {
        resources.add(res); // возвращение экземпляра в пул
        semaphore.release();
    }
}
