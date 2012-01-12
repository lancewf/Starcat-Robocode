package org.starcat.util;

/*
 * This is a class for a circular queue. Its primary intended purpose is for
 * supporting worker threads in a producer-consumer pattern. The producer 
 * thread and the consumer thread share a queue. The producer calls 
 * push() to add its output to the queue, while the consumer 
 * calls pop() to get its next job out of the queue.
 *
 * That said, this class is NOT thread-safe. It is typically 
 * used as a base class by SynchronizedCircularQueue which 
 * is thread-safe.
 *
 * This class works by maintaining a circular list of entries. 
 * You push() to one end and pop() from the other. 
 * If the number of entries you push() exceeds the number 
 * you have pop()'ed by more than the queue length 
 * (a construction parameter), then length of the queue
 *
 */
public class CircularQueue {
	
    private static final int DEFAULT_QUEUE_LENGTH = 3000;
    
    private int queueLength;
    private Object[] queue;
    private int head;
    private int tail;
    private int size;
    
    /**
     * Creates a new instance of CircularQueue of the 
     * default circular length.
     * The default is 3000.
     */
    public CircularQueue() {
        this(DEFAULT_QUEUE_LENGTH);
    }
    
    /**
     * Creates a new instance of CircularQueue with the given queue length.
     *
     */
    public CircularQueue(int queueLength) {
        //  Sanity check.
        if (queueLength < 1) {
            throw new IllegalArgumentException
			("Cannot form a queue of non-positive length");
        }
        this.queueLength = queueLength;
        this.queue = new Object[queueLength];
        // Just to be sure, initialize everything to null.
        for (int i=0; i<queueLength; i++) {
            queue[i] = null;
        }
        this.head = queueLength - 1;
        this.tail = queueLength - 1;
        size = 0;
    }
    
    /**
     * Adds an object to the queue. If the object to be queued is null, the
     * push is ignored.
     *
     */
    public void push(Object o) {
        if (o == null) {
            return;
        }
        
        //We add at the tail and remove from the head. So...
        //Advance the tail of the queue and check for wrap around.
        //this is complicated a bit by the fact that we might 
        //overwrite the head. If we do, then we need to advance 
        //the head. Fortunately, if
        //we advance the head, then the slot we advance it to is guaranteed
        //not to be null because we forbid enqueueing null objects.
        tail++;

        if (tail == queueLength) {
            tail = 0;
        }
        //If we are overwriting the head of the queue...
        if (tail == head && queue[head] != null) { 
            head++;
            if (head == queueLength) {
                head = 0;
            }
            size--;
        }        
        queue[tail] = o;
        
        //There is a special case here if the queue was empty. 
        //For instance,
        //when we first create it. If the queue was empty, then the 
        //head needs to be advanced so we can pop the right guy. 
        //If the queue was not empty, then the head is unchanged.
        if (queue[head] == null) {
            head = tail;
        }
        size++;
    }
    
    /**
     * Removes an object from the queue.
     *
     * @return The oldest object on the queue, or null 
     * if the queue is empty.
     */
    public Object pop() {
        //  Grab the result and empty the slot it was in.
        Object result = queue[head];
        queue[head] = null;
        
        //  Advance the head and check for wrap around.
        head++;
        if (head == queueLength) {
            head = 0;
        }
        
        if (isEmpty())
        {
        	size = 0;
        }
        else
        {
        	size--;
        }
        return result;
    }
    
    /**
     * If the queue was 
     * overwritten at any time, the size of the queue for the overwriten 
     * push(Object) operation remains unchanged, as one 
     * object was lost while another was put in its place.
     */
    public int size()
    {
    	return size;
    }
    
    public boolean isEmpty() {
        return queue[head] == null;
    }
}
