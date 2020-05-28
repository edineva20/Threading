package de.threads.extention;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SemphoreExample {

    public static final Logger LOGGER = Logger.getLogger(SemphoreExample.class.getName());
    public static final Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < 20; ++i) {
            new Thread(new SemphoreExampleWaiter()).start();
        }
    }
}

class SemphoreExampleWaiter implements  Runnable {

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while(true) {
            if(!SemphoreExample.semaphore.tryAcquire()) {
                SemphoreExample.LOGGER.log(Level.WARNING, "Could not aquire lock: {0},retrying", Thread.currentThread().getName()+ " " + SemphoreExample.semaphore.availablePermits());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            } else {
                SemphoreExample.LOGGER.log(Level.WARNING, "Lock successfully aquired : {0}", Thread.currentThread().getName()+ " " + SemphoreExample.semaphore.availablePermits());
                SemphoreExample.LOGGER.log(Level.WARNING, "Releasing lock : {0}", Thread.currentThread().getName() );
                SemphoreExample.semaphore.release();
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
