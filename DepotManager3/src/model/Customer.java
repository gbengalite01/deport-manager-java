/*
 * complete every method and constructor marked TODO.
 * Do NOT change the package, the class name, or any method or constructor
 * name, parameter list or return type. You may add private fields and
 * private helper methods.
 */
package model;

import java.util.Objects;

/**
 * Represents a customer waiting in the queue to collect a parcel.
 */
public class Customer {

    private int sequenceNumber;
    private String name;
    private String parcelId;

    /**
     * Creates a customer.
     *
     * @param sequenceNumber the customer's position number in the queue (must not be negative)
     * @param name           the customer's name (must not be null or blank)
     * @param parcelId       the ID of the parcel the customer wants (must not be null or blank)
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Customer(int sequenceNumber, String name, String parcelId) {
        // TODO: implement this constructor as described in the Javadoc above.
    	if (sequenceNumber < 0) {
            throw new IllegalArgumentException("Sequence number must not be negative.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank.");
        }
        if (parcelId == null || parcelId.isBlank()) {
            throw new IllegalArgumentException("Parcel ID must not be null or blank.");
        }
        
        this.sequenceNumber = sequenceNumber;
        this.name = name;
        this.parcelId = parcelId;
    }

    /**
     * @return the customer's sequence number
     */
    public int getSequenceNumber() {
        // TODO: implement this method as described in the Javadoc above.
    	return sequenceNumber;
    }

    /**
     * @return the customer's name
     */
    public String getName() {
        // TODO: implement this method as described in the Javadoc above.
    	return name;
    }

    /**
     * @return the ID of the parcel the customer wants to collect
     */
    public String getParcelId() {
        // TODO: implement this method as described in the Javadoc above.
    	return parcelId;
    }

    /**
     * Two customers are equal if they have the same sequence number, name
     * and parcel ID.
     *
     * @param o the object to compare with
     * @return true if o is an equal Customer
     */
    @Override
    public boolean equals(Object o) {
        // TODO: implement this method as described in the Javadoc above.
    	if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return sequenceNumber == customer.sequenceNumber &&
                Objects.equals(name, customer.name) &&
                Objects.equals(parcelId, customer.parcelId);
    }

    /**
     * @return a hash code consistent with equals (based on sequence number,
     *         name and parcel ID)
     */
    @Override
    public int hashCode() {
        // TODO: implement this method as described in the Javadoc above.
    	return Objects.hash(sequenceNumber, name, parcelId);
    }

    /**
     * @return a readable description that includes the sequence number,
     *         name and parcel ID
     */
    @Override
    public String toString() {
        // TODO: implement this method as described in the Javadoc above.
    	return "Customer{" +
        "sequenceNumber=" + sequenceNumber +
        ", name='" + name + '\'' +
        ", parcelId='" + parcelId + '\'' +
        '}';
    }
}
