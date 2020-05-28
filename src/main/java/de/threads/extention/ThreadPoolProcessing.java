package de.threads.extention;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ThreadPoolProcessing {

    private static final Logger LOGGER = Logger.getLogger(ThreadPoolProcessing.class.getName());
    public static final ArrayList<String> EREIGNIS = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
        es.scheduleAtFixedRate(new ThreadPoolTrigger(), 0, 5, TimeUnit.SECONDS);

        ExecutorService waiters = Executors.newFixedThreadPool(2000);
        for(int i = 0; i < 2000; ++i) {
            waiters.execute(new ThreadPoolWaiter());
        }
    }
}

class ThreadPoolTrigger implements  Runnable {
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
        ThreadPoolProcessing.EREIGNIS.add(new Date().toString());
        synchronized (ThreadPoolProcessing.EREIGNIS) {
            System.out.println(Thread.currentThread().getName().concat(" notifying at: ".concat(new Date().toString())));
            ThreadPoolProcessing.EREIGNIS.notifyAll();
        }
    }
}

class ThreadPoolWaiter implements  Runnable {
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
            synchronized (ThreadPoolProcessing.EREIGNIS) {
                try {
                    System.out.println(Thread.currentThread().getName().concat(" waiting since: ".concat(new Date().toString())));
                    ThreadPoolProcessing.EREIGNIS.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println(Thread.currentThread().getName().concat(" notifyed at: ".concat(new Date().toString())));
            System.out.flush();
        }
    }
}
