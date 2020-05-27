package de.threads.extention;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

public class SimpleWaiters {

    private static final Logger LOGGER = Logger.getLogger(SimpleWaiters.class.getName());
    public static final ArrayList<String> EREIGNIS = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        Thread waiter1 =new Thread(new Waiter());
        Thread waiter2 =new Thread(new Waiter());

        Thread trigger =new Thread(new Trigger());

        waiter1.start();
        waiter2.start();
        trigger.start();
    }
}

class Trigger implements  Runnable {
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
            try {
                // en event has occurred
                Thread.sleep(1000);

                synchronized (SimpleWaiters.EREIGNIS) {
                    SimpleWaiters.EREIGNIS.add(new Date().toString());
                    SimpleWaiters.EREIGNIS.notifyAll();
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

class Waiter implements  Runnable {
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
            synchronized (SimpleWaiters.EREIGNIS) {
                try {
                    SimpleWaiters.EREIGNIS.wait();
                    for(String line: SimpleWaiters.EREIGNIS) {
                        System.out.println(Thread.currentThread().getName().concat(" received : ").concat(line));
                        System.out.flush();
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
