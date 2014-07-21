package by.bsuir.tunnels.model;


import by.bsuir.tunnels.runner.TrainRunner;
import org.apache.log4j.Logger;

import java.util.Random;

public class Train extends Thread {

    private static Logger log = Logger.getLogger(Train.class);

    private int number;     // для идентификации поездов
    private Tunnel currentTunnel;
    private TrainRunner trainRunner;
    private long maxWaitSeconds;
    private String site;     // устанвливать время проезда в зависимости от длины поезда, в м

    public Train(int number, Tunnel currentTunnel, TrainRunner trainRunner, long maxWaitSeconds, String site) {
        this.number = number;
        this.currentTunnel = currentTunnel;
        this.trainRunner = trainRunner;
        this.maxWaitSeconds = maxWaitSeconds;
        this.site = site;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getMaxWaitSeconds() {
        return maxWaitSeconds;
    }

    public void setMaxWaitSeconds(long maxWaitSeconds) {
        this.maxWaitSeconds = maxWaitSeconds;
    }

    public void run() {

        log.info("Train #" + number + ", waiting for tunnel #" + currentTunnel.getNumber());

        while (!currentTunnel.getTunnelAsResource(maxWaitSeconds)) {
            int nextTunnelId = (trainRunner.getTunnels().indexOf(currentTunnel) + 1)%trainRunner.tunnelsListSize();
            currentTunnel = trainRunner.getTunnels().get(nextTunnelId);
            log.info("Train #" + number + " switched for waiting  for tunnel #" + currentTunnel.getNumber());
        }
        try {
            Thread.sleep(new Random().nextInt(5000)); // поезд проходит через тоннель
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

        //passed trough
        currentTunnel.getSemaphore().release();
        log.info("Train #" + number + " passed through tunnel #" + currentTunnel.getNumber());

    }

}
