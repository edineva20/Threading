package de.threads;

import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WaitForThreadToDie {

    private  static final Logger LOGGER = Logger.getLogger(WaitForThreadToDie.class.getName());

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, Thread> threads = new HashMap<>();

        LOGGER.log(Level.WARNING, "starting threads");

        for (int i = 0; i < 20; i++) {
            RandomRunner r = new RandomRunner(20);
            Thread t = new Thread(r);
            threads.put(t.getName(), t);
            t.start();
        }

        LOGGER.log(Level.WARNING, "waiting for threads to die");
        for (String name: threads.keySet()) {
            Thread t = threads.get(name);
            t.join();
            LOGGER.log(Level.WARNING, "Thread {0} died", t.getName());
        }
    }
}
