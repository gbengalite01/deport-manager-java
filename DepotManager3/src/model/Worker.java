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
    	if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Worker name must not be null or blank.");
        }
        this.name = name;
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
    	if (parcel == null) {
            return 0.0;
        }
        double baseFee = (parcel.getWeight() * 0.5) + (parcel.getVolume() * 0.1) + (parcel.getDaysInDepot() * 0.2);
        
        if (parcel.getId() != null && parcel.getId().startsWith("X")) {
            baseFee *= 0.9; // Apply 10% discount
        }
        
        // Round to 2 decimal places
        return Math.round(baseFee * 100.0) / 100.0;
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
    	if (queue == null || queue.isEmpty()) {
            return ProcessingResult.failure("Queue is empty.");
        }

        Customer customer = queue.peek();
        if (customer == null) {
            return ProcessingResult.failure("No customer at the front of the queue.");
        }

        if (parcels == null) {
            return ProcessingResult.failure("Parcel map is null.");
        }

        Parcel parcel = parcels.getParcel(customer.getParcelId());
        if (parcel == null) {
            return ProcessingResult.failure("Parcel not found: " + customer.getParcelId());
        }

        if (parcel.isCollected()) {
            return ProcessingResult.failure("Parcel already collected: " + parcel.getId());
        }

        // Remove customer from the queue since we are processing them
        queue.dequeue();

        // Calculate fee and mark parcel as collected
        double fee = calculateFee(parcel);
        parcel.setCollected(true);

        // Log the successful event
        String logMessage = String.format("Worker %s processed customer %s (%d). Parcel %s collected, fee £%.2f",
                name, customer.getName(), customer.getSequenceNumber(), parcel.getId(), fee);
        Log.getInstance().logEvent(logMessage);

        return ProcessingResult.success(customer, parcel, fee);
    }
}
