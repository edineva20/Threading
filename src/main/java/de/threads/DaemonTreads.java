package de.threads;

import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaemonTreads {

    private  static final Logger LOGGER = Logger.getLogger(DaemonTreads.class.getName());

    private static class RandomRunner implements  Runnable {
        private boolean running = true;

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
            while(running) {
                try {
                    Thread.sleep(randomSleep.nextInt(1000));
                } catch (InterruptedException e) {
                    LOGGER.log(Level.WARNING, "Thread {0} interrupted", Thread.currentThread().getName());
                    e.printStackTrace();
                    running = false;
                }
                LOGGER.log(Level.INFO, "{0} still running", Thread.currentThread().getName());
           }
           LOGGER.log(Level.INFO, "{0} stopped", Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, RandomRunner> runnables = new HashMap<>();

        for (int i = 0; i < 20; i++) {
            RandomRunner r = new RandomRunner();
            Thread t = new Thread(r);
            t.setDaemon(true);
            runnables.put(t.getName(), r);
            t.start();
        }
        Thread.sleep(2000);
        // DaemonTreads will exit even with still running threads.
    }
}
