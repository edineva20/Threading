package de.threads.extention;

import de.threads.RandomRunner;

import java.util.HashMap;
import java.util.logging.Logger;

public class SimpleTreading {

    private static final Logger LOGGER = Logger.getLogger(SimpleTreading.class.getName());

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, RandomRunner> runnables = new HashMap<>();
        HashMap<String, Thread> threads = new HashMap<>();

        for (int i = 0; i < 20; i++) {
            RandomRunner r = new RandomRunner();
            Thread t = new Thread(r);
            runnables.put(t.getName(), r);
            threads.put(t.getName(), t);
            t.start();
        }

        Thread.sleep(2000);
        for (String name : runnables.keySet()) {
            RandomRunner t = runnables.get(name);
            t.stop();
        }

        Thread.sleep(2000);
        for (String name : threads.keySet()) {
            Thread t = threads.get(name);
            t.join();
        }

        //ILLEGAL start() call
        //Thread.sleep(2000);
        //for (String name : threads.keySet()) {
        //    Thread t = threads.get(name);
        //    t.start();
        //}
    }
}
