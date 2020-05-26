package de.threads;

import de.threads.executor.SingleThreadAccess;

public class SingleThreadTest {

    public static void main(String[] args) {
        int nTasks = Integer.parseInt(args[0]);
        int fib = Integer.parseInt(args[1]);
        SingleThreadAccess sta = new SingleThreadAccess();
        for (int i = 0; i < nTasks; i++)
            sta.invokeLater(new Task(fib, "Task " + i));
        sta.shutdown();
    }
}
