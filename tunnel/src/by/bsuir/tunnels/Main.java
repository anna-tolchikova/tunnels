package by.bsuir.tunnels;

import by.bsuir.tunnels.pool.Train;
import by.bsuir.tunnels.pool.Tunnel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private static Logger log = Logger.getLogger(Tunnel.class);

    static {
        new DOMConfigurator().doConfigure("log4j.xml", LogManager.getLoggerRepository());
    }

    public static void main(String[] args) {

        Tunnel firstTunnel = new Tunnel(1);
        Tunnel secondTunnel = new Tunnel(2);
//        TunnelsPool tunnelsPool = new TunnelsPool(new LinkedList<Tunnel>() {
//            {
//                this.add(firstTunnel);
//                this.add(secondTunnel);
//            }
//        });

        ExecutorService es = Executors.newSingleThreadExecutor();

        String site;
        Queue<Train> firstTunnelLeftQueue = new LinkedList<Train>();
        Queue<Train> firstTunnelRightQueue = new LinkedList<Train>();
        Queue<Train> secondTunnelLeftQueue = new LinkedList<Train>();
        Queue<Train> secondTunnelRightQueue = new LinkedList<Train>();
        for (int i = 0 ; i < 5 ; ++i) {
            if (new Random().nextInt(100)%2 == 0) {
                site = "left";
                Train newTrain = new Train(i+1, firstTunnel, secondTunnel, 3, 30+i*5, site);
                firstTunnelLeftQueue.add(newTrain);
//                newTrain.start();
            }
            else {
                site = "right";
                Train newTrain = new Train(i+1, firstTunnel, secondTunnel, 3, 30+i*5, site);
                firstTunnelRightQueue.add(newTrain);
//                newTrain.start();
            }
        }

        for (int i = 5 ; i < 10 ; ++i) {
            if (new Random().nextInt(100)%2 == 0) {
                site = "left";
                Train newTrain = new Train(i+1, secondTunnel, firstTunnel, 3, 30+i*5, site);
                secondTunnelLeftQueue.add(newTrain);
//                newTrain.start();
            }
            else {
                site = "right";
                Train newTrain = new Train(i+1, secondTunnel, firstTunnel, 3, 30+i*5, site);
                secondTunnelLeftQueue.add(newTrain);
//                newTrain.start();
            }
        }

        // ПРЕДПОЛАГАЕТСЯ, ЧТО НОВЫЕ ПОЕЗДА НЕ ПОДЪЕЗЖАЮТ :(((

        while (firstTunnelLeftQueue.size() > 0 || firstTunnelRightQueue.size() > 0) {

            while (firstTunnelLeftQueue.size() > firstTunnelRightQueue.size()) {
                Train train = firstTunnelLeftQueue.poll();
                Future<Boolean> future = es.submit(train);
                try {
                    if (future.get() == false) {
                        Tunnel tmp = train.getMainTunnel();
                        train.setMainTunnel(train.getSecondTunnel());
                        train.setSecondTunnel(tmp);
                        secondTunnelLeftQueue.add(train);
                    }
                    else {
                        log.info("Train #" + train.getNumber() + " goes form tunnel #" + train.getMainTunnel().getNumber());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    log.error("First tunnel: " + e.getMessage());
                }
            }

            while (firstTunnelLeftQueue.size() < firstTunnelRightQueue.size()) {
                Train train = firstTunnelRightQueue.poll();
                Future<Boolean> future = es.submit(train);
                try {
                    if (future.get() == false) {
                        Tunnel tmp = train.getMainTunnel();
                        train.setMainTunnel(train.getSecondTunnel());
                        train.setSecondTunnel(tmp);
                        secondTunnelRightQueue.add(train);
                    }
                    else {
                        log.info("Train #" + train.getNumber() + " goes form tunnel #" + train.getMainTunnel().getNumber());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    log.error("First tunnel: " + e.getMessage());
                }
            }

            Train train = firstTunnelLeftQueue.poll();
            Future<Boolean> future = es.submit(train);
            try {
                if (future.get() == false) {
                    Tunnel tmp = train.getMainTunnel();
                    train.setMainTunnel(train.getSecondTunnel());
                    train.setSecondTunnel(tmp);
                    secondTunnelLeftQueue.add(train);
                }
                else {
                    log.info("Train #" + train.getNumber() + " goes form tunnel #" + train.getMainTunnel().getNumber());
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("First tunnel: " + e.getMessage());
            }

            train = firstTunnelRightQueue.poll();
            future = es.submit(train);
            try {
                if (future.get() == false) {
                    Tunnel tmp = train.getMainTunnel();
                    train.setMainTunnel(train.getSecondTunnel());
                    train.setSecondTunnel(tmp);
                    secondTunnelRightQueue.add(train);
                }
                else {
                    log.info("Train #" + train.getNumber() + " goes form tunnel #" + train.getMainTunnel().getNumber());
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("First tunnel: " + e.getMessage());
            }



        }





        while (secondTunnelLeftQueue.size() > 0 || secondTunnelRightQueue.size() > 0) {

            while (secondTunnelLeftQueue.size() > secondTunnelRightQueue.size()) {
                Train train = firstTunnelLeftQueue.poll();
                Future<Boolean> future = es.submit(train);
                try {
                    if (future.get() == false) {
                        Tunnel tmp = train.getMainTunnel();
                        train.setMainTunnel(train.getSecondTunnel());
                        train.setSecondTunnel(tmp);
                        firstTunnelLeftQueue.add(train);
                    }
                    else {
                        log.info("Train #" + train.getNumber() + " goes form tunnel #" + train.getMainTunnel().getNumber());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    log.error("First tunnel: " + e.getMessage());
                }
            }

            while (secondTunnelLeftQueue.size() < secondTunnelRightQueue.size()) {
                Train train = firstTunnelRightQueue.poll();
                Future<Boolean> future = es.submit(train);
                try {
                    if (future.get() == false) {
                        Tunnel tmp = train.getMainTunnel();
                        train.setMainTunnel(train.getSecondTunnel());
                        train.setSecondTunnel(tmp);
                        firstTunnelRightQueue.add(train);
                    }
                    else {
                        log.info("Train #" + train.getNumber() + " goes form tunnel #" + train.getMainTunnel().getNumber());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    log.error("First tunnel: " + e.getMessage());
                }
            }

            Train train = secondTunnelLeftQueue.poll();
            Future<Boolean> future = es.submit(train);
            try {
                if (future.get() == false) {
                    Tunnel tmp = train.getMainTunnel();
                    train.setMainTunnel(train.getSecondTunnel());
                    train.setSecondTunnel(tmp);
                    firstTunnelLeftQueue.add(train);
                }
                else {
                    log.info("Train #" + train.getNumber() + " goes form tunnel #" + train.getMainTunnel().getNumber());
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("First tunnel: " + e.getMessage());
            }

            train = secondTunnelRightQueue.poll();
            future = es.submit(train);
            try {
                if (future.get() == false) {
                    Tunnel tmp = train.getMainTunnel();
                    train.setMainTunnel(train.getSecondTunnel());
                    train.setSecondTunnel(tmp);
                    firstTunnelRightQueue.add(train);
                }
                else {
                    log.info("Train #" + train.getNumber() + " goes form tunnel #" + train.getMainTunnel().getNumber());
                }
            } catch (InterruptedException | ExecutionException e) {
                log.error("First tunnel: " + e.getMessage());
            }



        }







    }
}
