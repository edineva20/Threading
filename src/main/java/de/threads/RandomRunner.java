package de.threads;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class RandomRunner implements  Runnable {
    private boolean running = true;
    private  static final Logger LOGGER = Logger.getLogger(RandomRunner.class.getName());

    private int maximumLoops = -1;
    public RandomRunner(int maximumLoops) {
        this.maximumLoops = maximumLoops;
    }

    public RandomRunner() {
    }

    public void stop() {
        running = false;
    }

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
        Random randomSleep = new Random();
        while(running && maximumLoops != 0) {
            if (maximumLoops > 0) {
                --maximumLoops;
                try {
                    Thread.sleep(randomSleep.nextInt(1000));
                } catch (InterruptedException e) {
                    RandomRunner.LOGGER.log(Level.WARNING, "Thread {0} interrupted", Thread.currentThread().getName());
                    e.printStackTrace();
                    running = false;
                }
                RandomRunner.LOGGER.log(Level.INFO, "{0} still running", Thread.currentThread().getName());
            }
        }
        RandomRunner.LOGGER.log(Level.INFO, "{0} stopped", Thread.currentThread().getName());
    }
}
