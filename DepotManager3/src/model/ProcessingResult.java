/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package model;

/**
 * Immutable value object describing the outcome of a single call to
 * {@link Worker#processNextCustomer(CustomerQueue, ParcelMap)}.
 */
public final class ProcessingResult {

	private final boolean success;
    private final String message;
    private final Customer customer;
    private final Parcel parcel;
    private final double fee;

    /**
     * Private constructor: instances are created only through the static
     * factory methods success(...) and failure(...).
     */
    private ProcessingResult(boolean success, String message, Customer customer, Parcel parcel, double fee) {
        this.success = success;
        this.message = message;
        this.customer = customer;
        this.parcel = parcel;
        this.fee = fee;
    }

    /**
     * Creates a successful result. The message has the form
     * "Parcel [parcelId] collected by [customerName], fee £[fee]".
     *
     * @param customer the customer who collected the parcel
     * @param parcel   the parcel collected
     * @param fee      the fee charged
     * @return a result for which isSuccess() returns true
     */
    public static ProcessingResult success(Customer customer, Parcel parcel, double fee) {
        // TODO: implement this method as described in the Javadoc above.
    	
    }

    /**
     * Creates a failed result with the given message. The customer and
     * parcel are null and the fee is 0.0.
     *
     * @param message a description of why processing failed
     * @return a result for which isSuccess() returns false
     */
    public static ProcessingResult failure(String message) {
        // TODO: implement this method as described in the Javadoc above.
    	return new ProcessingResult(false, message, null, null, 0.0);
    }

    /**
     * @return true if the customer was processed successfully
     */
    public boolean isSuccess() {
        // TODO: implement this method as described in the Javadoc above.
    	return success;
    }

    /**
     * @return a description of the outcome
     */
    public String getMessage() {
        // TODO: implement this method as described in the Javadoc above.
    	return message;
    }

    /**
     * @return the customer processed, or null for a failed result
     */
    public Customer getCustomer() {
        // TODO: implement this method as described in the Javadoc above.
    	return customer;
    }

    /**
     * @return the parcel collected, or null for a failed result
     */
    public Parcel getParcel() {
        // TODO: implement this method as described in the Javadoc above.
    	return parcel;
    }

    /**
     * @return the fee charged, or 0.0 for a failed result
     */
    public double getFee() {
        // TODO: implement this method as described in the Javadoc above.
    	return fee;
    }

    /**
     * @return the message describing the outcome
     */
    @Override
    public String toString() {
        // TODO: implement this method as described in the Javadoc above.
    	return message;
    }
}
