package by.bsuir.tunnels.pool;


import by.bsuir.tunnels.exceptions.ResourceException;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.Callable;


public class Train implements Callable<Boolean> {

    private static Logger log = Logger.getLogger(Train.class);

    private int number;     // для идентификации поездов
    private Tunnel mainTunnel;
    private Tunnel secondTunnel;
    private int maxWaitSeconds;
    private int length;     // устанвливать время проезда в зависимости от длины поезда, в м
    private String site;

    public Train(int number, Tunnel mainTunnel, Tunnel secondTunnel, int maxWaitSeconds,int length, String site) {
        this.number = number;
        this.mainTunnel = mainTunnel;
        this.secondTunnel = secondTunnel;
        this.maxWaitSeconds = maxWaitSeconds;
        this.length = length;
        this.site = site;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public Tunnel getMainTunnel() {
        return mainTunnel;
    }

    public void setMainTunnel(Tunnel mainTunnel) {
        this.mainTunnel = mainTunnel;
    }

    public Tunnel getSecondTunnel() {
        return secondTunnel;
    }

    public void setSecondTunnel(Tunnel secondTunnel) {
        this.secondTunnel = secondTunnel;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
//
//    public TunnelsPool getPool() {
//        return pool;
//    }
//
//    public void setPool(TunnelsPool pool) {
//        this.pool = pool;
//    }

    public int getMaxWaitSeconds() {
        return maxWaitSeconds;
    }

    public void setMaxWaitSeconds(int maxWaitSeconds) {
        this.maxWaitSeconds = maxWaitSeconds;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Boolean call() {

        return mainTunnel.using(maxWaitSeconds, length, number, site);



//        try {
//            freeTunnel = pool.getResource(maxWaitSeconds, site);
//            while (freeTunnel == null) {
//                log.info("Train #" + number + ", waiting for tunnel " + maxWaitSeconds + " seconds");
//                freeTunnel = pool.getResource(maxWaitSeconds, site);
//            }
//            log.info("Train #" + number + ", catched tunnel #"+ freeTunnel.getNumber());
//            this.passThrough(freeTunnel.getNumber());
//            pool.returnResource(freeTunnel);
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
