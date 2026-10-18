/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
//import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Container class maintaining the queue of customers waiting to collect
 * their parcels, in strict first-in-first-out order.
 */
public class CustomerQueue {

    private final Queue<Customer> queue = new LinkedList<>();

    /**
     * Adds a customer to the back of the queue.
     *
     * @param customer the customer to add
     * @throws IllegalArgumentException if customer is null
     */
    public void enqueue(Customer customer) {
        // TODO: implement this method as described in the Javadoc above.
    	if (customer == null) {
            throw new IllegalArgumentException("Customer must not be null.");
        }
        queue.offer(customer);
    }

    /** Removes and returns the customer at the front of the queue, or null if empty. */
    public Customer dequeue() {
        // TODO: implement this method as described in the Javadoc above.
    	return queue.poll();
    }

    /** Returns the customer at the front of the queue without removing them, or null if empty. */
    public Customer peek() {
        // TODO: implement this method as described in the Javadoc above.
    	return queue.peek();
    }

    /**
     * @return true if no customers are waiting
     */
    public boolean isEmpty() {
        // TODO: implement this method as described in the Javadoc above.
    	return queue.isEmpty();
    }

    /**
     * @return the number of customers waiting
     */
    public int size() {
        // TODO: implement this method as described in the Javadoc above.
    	return queue.size();
    }

    /**
     * Removes a specific customer (by sequence number) from the queue,
     * wherever they are in it, e.g. if they cancel while waiting.
     * Returns true if a matching customer was found and removed.
     */
    public boolean removeBySequenceNumber(int sequenceNumber) {
        // TODO: implement this method as described in the Javadoc above.
    	return queue.removeIf(customer -> customer.getSequenceNumber() == sequenceNumber);
    }

    /** An unmodifiable, ordered snapshot of the current queue, for display purposes. */
    public List<Customer> getQueueSnapshot() {
        // TODO: implement this method as described in the Javadoc above.
    	return Collections.unmodifiableList(new ArrayList<>(queue));
    }
}
