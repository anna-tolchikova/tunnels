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
    }
}
