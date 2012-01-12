package org.starcat.util;

/**
 * This is a thread-safe versio of the CircularQueue class. It does 
 * what that
 * class does in a thread-safe way.
 *
 */
public class SynchronizedCircularQueue extends CircularQueue 
{
    /*
     * Create a new instance of a synchronized circular queue with a 
     * buffer of
     * the default size.
     */
    public SynchronizedCircularQueue() {
        super();
    }
    
    /*
     * Creates a new instance of a synchronized circular queue with a 
     * buffer of
     * the given size.
     *
     */
    public SynchronizedCircularQueue(int queueLength) {
        super(queueLength);
    }
    
    public void push(Object o) {
        synchronized (this) {
            super.push(o);
            notifyAll();
        }
    }
    
    /*
     * Returns the oldest item in the queue or null if the queue is empty.
     *
     */
    public Object pop() {
        synchronized (this) {
            while (isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException ie) {}
            }
            notifyAll();
            return super.pop();
        }
    }
    
    /*
     * Returns true if there are no items pending in the queue, 
     * false otherwise.
     *
     */
    public boolean isEmpty() {
        synchronized (this) {
            return super.isEmpty();
        }
    }
}
