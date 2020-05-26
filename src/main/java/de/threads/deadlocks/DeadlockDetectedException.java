package de.threads.deadlocks;

public class DeadlockDetectedException extends RuntimeException {

    public DeadlockDetectedException(String s) {
        super(s);
    }
}
