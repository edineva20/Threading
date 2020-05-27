package de.threads.extention;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWaiters {
    public static final ArrayList<String> EREIGNIS = new ArrayList<>();
    public static final Lock lock = new ReentrantLock();
    public static final Condition cv = lock.newCondition();
    
    public static void main(String[] args) throws InterruptedException {

        Thread waiter1 =new Thread(new ReentrantWaiter());
        Thread waiter2 =new Thread(new ReentrantWaiter());

        Thread trigger =new Thread(new ReentrantTrigger());

        waiter1.start();
        waiter2.start();
        trigger.start();
    }
}

class ReentrantTrigger implements  Runnable {
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
                Thread.sleep(5000);
                try {
                    ReentrantLockWaiters.lock.lock();

                    ReentrantLockWaiters.EREIGNIS.add(new Date().toString());
                    System.out.println(Thread.currentThread().getName().concat(" notifying at: ".concat(new Date().toString())));

                    ReentrantLockWaiters.cv.signalAll();
                } finally {
                    ReentrantLockWaiters.lock.unlock();
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

class ReentrantWaiter implements  Runnable {
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
            ReentrantLockWaiters.lock.lock();
            try {
                System.out.println(Thread.currentThread().getName().concat(" waiting since: ".concat(new Date().toString())));
                ReentrantLockWaiters.cv.await();
            } catch (InterruptedException e) {
                break;
            } finally {
                ReentrantLockWaiters.lock.unlock();
            }

            System.out.println(Thread.currentThread().getName().concat(" notifyed at: ".concat(new Date().toString())));

            ReentrantLockWaiters.lock.lock();
            try {
                for (String line : ReentrantLockWaiters.EREIGNIS) {
                        System.out.println(Thread.currentThread().getName().concat(" received : ").concat(line));
                }
            } finally {
                ReentrantLockWaiters.lock.unlock();
            }

            System.out.flush();
        }
    }
}
