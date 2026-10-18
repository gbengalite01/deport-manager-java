/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package model;

/**
 * Represents a depot worker: the class responsible for processing
 * customers from the queue, calculating the collection fee for their
 * parcel, and releasing (marking as collected) that parcel.
 *
 * Fee formula (fixed and deterministic, for testability):
 *   fee = (weight * 0.5) + (volume * 0.1) + (daysInDepot * 0.2)
 *   if the parcel ID starts with 'X', a 10% discount is then applied
 *   the result is rounded to 2 decimal places
 */
public class Worker {

    private String name;

    /**
     * Creates a worker.
     *
     * @param name the worker's name (must not be null or blank)
     * @throws IllegalArgumentException if name is null or blank
     */
    public Worker(String name) {
        // TODO: implement this constructor as described in the Javadoc above.
    	
    }

    /**
     * @return the worker's name
     */
    public String getName() {
        // TODO: implement this method as described in the Javadoc above.
    	return name;
    }

    /**
     * Calculates the collection fee for the given parcel using the fixed
     * formula described in the class documentation above.
     */
    public double calculateFee(Parcel parcel) {
        // TODO: implement this method as described in the Javadoc above.
    	
    }

    /**
     * Processes the customer at the front of the queue: looks up their
     * parcel, calculates the fee, marks the parcel as collected, logs the
     * event, and returns a ProcessingResult describing what happened.
     *
     * Returns a failure ProcessingResult (never null, never throws) if the
     * queue is empty, if the customer's parcel cannot be found, or if that
     * parcel has already been collected.
     */
    public ProcessingResult processNextCustomer(CustomerQueue queue, ParcelMap parcels) {
        // TODO: implement this method as described in the Javadoc above.
    	

        
    }
}
