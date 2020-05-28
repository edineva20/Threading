package de.threads.extention;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLocks {
    public static final ArrayList<String> EREIGNIS = new ArrayList<>();
    public static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public static void main(String[] args) throws InterruptedException {

        for(int i = 0; i < 20; ++i) {
            new Thread(new ReadWriteLocksReader()).start();
        }
        new Thread(new ReadWriteLocksWriter()).start();
    }
}

class ReadWriteLocksWriter implements  Runnable {
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
                if(ReadWriteLocks.lock.writeLock().tryLock()) {
                    try {
                        ReadWriteLocks.EREIGNIS.add(new Date().toString());
                        // downgrade after write completion
                        ReadWriteLocks.lock.readLock().lock();
                    } finally {
                        ReadWriteLocks.lock.writeLock().unlock();
                    }

                    try {
                        System.out.println(Thread.currentThread().getName().concat(" has written new data at: ".concat(new Date().toString())));
                        for (String line : ReadWriteLocks.EREIGNIS) {
                            System.out.println(Thread.currentThread().getName().concat(" currently stored : ").concat(line));
                            Thread.sleep(0, 10);
                        }
                    } finally {
                        ReadWriteLocks.lock.readLock().unlock();
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

class ReadWriteLocksReader implements  Runnable {
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
            ReadWriteLocks.lock.readLock().lock();
            try {
                for (String line : ReadWriteLocks.EREIGNIS) {
                    System.out.println(Thread.currentThread().getName().concat(" received : ").concat(line));
                    try {
                        Thread.sleep(0, 10);
                    } catch (InterruptedException e) {
                        break;
                    }                }
            } finally {
                ReadWriteLocks.lock.readLock().unlock();
            }
            System.out.flush();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
