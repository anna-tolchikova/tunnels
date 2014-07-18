package by.bsuir.tunnels;

import by.bsuir.tunnels.model.Train;
import by.bsuir.tunnels.model.Tunnel;
import by.bsuir.tunnels.runner.TrainRunner;
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


        TrainRunner runner = new TrainRunner();
        runner.initializeTrainsQueue();
        try {
            runner.beginSimulation();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }


//
//            while (secondTunnelLeftQueue.size() < secondTunnelRightQueue.size()) {
//                Train train = secondTunnelRightQueue.poll();
//                Future<Boolean> future = es.submit(train);
//                try {
//                    if (future.get() == false) {
//                        Tunnel tmp = train.getMainTunnel();
//                        train.setMainTunnel(train.getSecondTunnel());
//                        train.setSecondTunnel(tmp);
//                        firstTunnelRightQueue.add(train);
//                        log.info("Train #" + train.getNumber() + " goes from tunnel #" + train.getMainTunnel().getNumber());
//                    }
//                    else {
//                        log.info("Train #" + train.getNumber() + " passed through tunnel #" + train.getMainTunnel().getNumber());
//                    }
//                } catch (InterruptedException | ExecutionException e) {
//                    log.error("First tunnel: " + e.getMessage());
//                }
//            }








    }
}
