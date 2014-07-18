package by.bsuir.tunnels.model;


import by.bsuir.tunnels.runner.TrainRunner;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.Semaphore;


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

        while (!currentTunnel.getTunnelAsResource(maxWaitSeconds)) {
            int nextTonnelId = (trainRunner.getTunnels().indexOf(currentTunnel) + 1)%trainRunner.tunnelsListSize();
            currentTunnel = trainRunner.getTunnels().get(nextTonnelId);
        }
        try {
            Thread.sleep(new Random().nextInt(5000)); // поезд проходит через тоннель
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

        //passed trough
        currentTunnel.getSemaphore().release();

//        try {
//            freeTunnel = model.getResource(maxWaitSeconds, site);
//            while (freeTunnel == null) {
//                log.info("Train #" + number + ", waiting for tunnel " + maxWaitSeconds + " seconds");
//                freeTunnel = model.getResource(maxWaitSeconds, site);
//            }
//            log.info("Train #" + number + ", catched tunnel #"+ freeTunnel.getNumber());
//            this.passThrough(freeTunnel.getNumber());
//            model.returnResource(freeTunnel);
//            log.info("Train #" + number + ", catched tunnel #"+ freeTunnel.getNumber() + " passed through");

//        } catch (ResourceException e) {
//            log.error("Train #" + number + ", waiting for tunnel "+ maxWaitSeconds + e.getMessage());
//        }


    }

//    public void passThrough(int tunnelNumber) {
//        try {
//            Thread.sleep(new Random().nextInt(10000 + length*100/60));  // допускаем, что время нахождения в туннеле  1с(реальная 1мин) + время, зависящее от длины.
//        } catch (InterruptedException e) {
//            log.error("Train #" + number + "passing through tunnel #" + tunnelNumber + ": " + e.getMessage());
//        }
//    }
}
