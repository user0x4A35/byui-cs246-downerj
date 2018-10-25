package io.github.user0x4a35.asyncexample;

/**
 * PROCESS LOCK
 * This holds a single integer which can be "acquired" or set to a random integer and then
 * given to a new thread process. It can only be acquired if it's not already set and must be
 * "released" or reset by the current process before it completes.
 * @author James D. Downer
 * @since October 24, 2018
 * @version 1.0
 */
public class ProcessLock {
    private static final double ID_MAX = (double) (Integer.MAX_VALUE - 1.0);
    private int id;

    /**
     * PROCESS LOCK : CONSTRUCTOR
     * Sets the initial ID to 0 ("released").
     */
    public ProcessLock() {
        id = 0;
    }

    /**
     * PROCESS LOCK : ACQUIRE
     * Returns a new random ID if available or 0 if already taken.
     * @return The new ID
     */
    public int acquire() {
        if (id == 0) {
            id = generateId();
            return id;
        } else {
            return 0;
        }
    }

    /**
     * PROCESS LOCK : RELEASE
     * Resets the ID if it matches the ID parameter.
     * @param id The ID to match and release
     */
    public void release(int id) {
        if (this.id == id) {
            this.id = 0;
        }
    }

    /**
     * PROCESS LOCK : GENERATE ID
     * This generates a new random integer between 0 and Integer.MAX_VALUE - 1.
     * @return The new integer
     */
    private int generateId() {
        return (int) (Math.random() * ID_MAX + 1.0);
    }
}
