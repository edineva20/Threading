package de.threads;

import java.util.concurrent.ArrayBlockingQueue;

public class FibonacciTest {

    public static void main(String[] args) {

        ArrayBlockingQueue<Integer> queue;
        queue = new ArrayBlockingQueue<Integer>(10);
        new FibonacciProducer(queue);

        int nThreads = Integer.parseInt(args[0]);
        for (int i = 0; i < nThreads; i++)
            new FibonacciConsumer(queue);
    }
}
