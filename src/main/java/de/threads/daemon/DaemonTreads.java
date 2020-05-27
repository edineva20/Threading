package de.threads.daemon;

import de.threads.RandomRunner;

import java.util.HashMap;
import java.util.logging.Logger;

public class DaemonTreads {

    private static final Logger LOGGER = Logger.getLogger(DaemonTreads.class.getName());

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, RandomRunner> runnables = new HashMap<>();

        for (int i = 0; i < 20; i++) {
            RandomRunner r = new RandomRunner();
            Thread t = new Thread(r);
            t.setDaemon(true);
            runnables.put(t.getName(), r);
            t.start();
            // business logic
        }

        Thread.sleep(2000);
        // DaemonTreads will exit even with still running threads.
    }
}
