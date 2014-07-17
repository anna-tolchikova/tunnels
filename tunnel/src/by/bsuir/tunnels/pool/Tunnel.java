package by.bsuir.tunnels.pool;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tunnel{

    private static Logger log = Logger.getLogger(Tunnel.class);



    private int number;
    private Lock lock = new ReentrantLock();
    private Condition isFreeForLeft = lock.newCondition();
    private Condition isFreeForRight = lock.newCondition();
    private boolean isUsedLeft = false;
    private boolean isUsedRight = false;
//    private Queue<Train> leftQueue;
//    private Queue<Train> rightQueue;

    public Tunnel(int number) {
        this.number = number;
//        leftQueue = new LinkedList<Train>();
//        rightQueue = new LinkedList<Train>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

//    public void addTrainLeft(Train train) {
//        leftQueue.add(train);
//    }
//
//    public void removeTrainLeft(){
//        leftQueue.poll();
//    }
//
//    public void addTrainRight(Train train) {
//        rightQueue.add(train);
//    }
//
//    public void removeTrainRight(){
//        rightQueue.poll();
//    }
//


    public boolean using(int maxWaitSeconds, int length, int trainNumber, String site) {
        boolean isPassedThrough = false;
        try {

            log.info("Train #" + trainNumber + ", waiting for tunnel #" + number + " for max = " + maxWaitSeconds + " seconds");

            if (lock.tryLock(maxWaitSeconds, TimeUnit.SECONDS)) {
                try {
                    if("left".equals(site)) {
                        while (isUsedRight == true) {
                            isFreeForLeft.await();
                        }
                        Thread.sleep(new Random().nextInt(10000 + length*100/60));  // допускаем, что время нахождения в туннеле  1с(реальная 1мин) + время, зависящее от длины.
                        isUsedLeft = false;
                        isFreeForRight.signal();
                    }
                    if("right".equals(site)) {
                        while (isUsedLeft == true) {
                            isFreeForRight.await();
                        }
                        Thread.sleep(new Random().nextInt(10000 + length*100/60));  // допускаем, что время нахождения в туннеле  1с(реальная 1мин) + время, зависящее от длины.
                        isUsedRight = false;
                        isFreeForLeft.signal();
                    }


                } catch (InterruptedException e) {
                    log.error("Train #" + number + "passing through tunnel #" + number + ": " + e.getMessage());
                };
                isPassedThrough = true;
            }
            else {
                log.info("Train #" + trainNumber + ", waited for " + site + " tunnel #" + number + " did not wait");
            }
        } catch (InterruptedException e) {
            log.error("Train #" + trainNumber + "passing through tunnel #" + number + ": " + e.getMessage());
        } finally {
            if (isPassedThrough == true) {
                lock.unlock();
            }
        }
        return isPassedThrough;
    }
}
