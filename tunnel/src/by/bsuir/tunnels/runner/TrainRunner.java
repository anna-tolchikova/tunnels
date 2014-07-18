package by.bsuir.tunnels.runner;

import by.bsuir.tunnels.model.Train;
import by.bsuir.tunnels.model.Tunnel;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TrainRunner {

    private static Logger log = Logger.getLogger(TrainRunner.class);


    private final int START_QUEUES_LENGTH = 20;
    private final long MAX_WAIT_SECONDS = 5;
    private ArrayList<Tunnel> tunnels;
    private ArrayList<Train> trains;
    private Random rnd;


    public TrainRunner() {
        tunnels = new ArrayList<Tunnel>();
        tunnels.add(new Tunnel(1));
        tunnels.add(new Tunnel(2));
        trains = new ArrayList<Train>();
        rnd = new Random();
    }



    private Train CreateTrain()
    {
        Train train = new Train(trains.size() + 1, tunnels.get(rnd.nextInt(tunnels.size())), this, MAX_WAIT_SECONDS, (rnd.nextInt(2) == 0) ? "left" : "right");
        return train;
    }

    public int tunnelsListSize() {
        return tunnels.size();
    }

    public ArrayList<Tunnel> getTunnels() {
        return tunnels;
    }

    public void initializeTrainsQueue() {
        for (int i = 0; i < START_QUEUES_LENGTH; ++i)
        {
            trains.add(CreateTrain());
        }
    }

    public void beginSimulation() throws InterruptedException {
        for (Train train: trains)
        {
            train.start();
        }

        while (true)
        {
            Train newTrain = CreateTrain();
            trains.add(newTrain);
            newTrain.start();

            Thread.sleep(rnd.nextInt(10000));
        }
    }

}
