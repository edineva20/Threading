package de.threads.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerExample {
    public  static  void  main(String [] args) {
        Timer t = new Timer();
        TimerTask task = new MyTask("a");
        t.schedule(task, 0 , 1000L);
        t.schedule(new MyTask("b"), 0 , 2200L);
        t.schedule(new MyTask("c"), 0 , 3300L);
        t.schedule(new MyTask("d"), 0 , 1500L);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        task.cancel();

    }
}

class MyTask extends TimerTask {
    private String name;
    public MyTask(String name) {
        super();
        this.name = name;
    }
    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName().concat(" ").concat(name).concat(" ").concat(new Date().toString()));
    }
}